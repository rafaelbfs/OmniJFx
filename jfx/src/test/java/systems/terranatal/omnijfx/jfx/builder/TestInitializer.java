package systems.terranatal.omnijfx.jfx.builder;

import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import systems.terranatal.omnijfx.jfx.datautils.Converters;

import java.util.function.Function;

import static java.lang.Math.PI;

@ExtendWith(ApplicationExtension.class)
public class TestInitializer {
    private final Function<Double, Double> degsToRadians = degs -> degs * PI/180.0;
    private final Function<Double, Double> radsToDegrees = rads -> rads * 180.0/PI;

    private final Function<Number, Double> doubleValue = Number::doubleValue;
    Slider slider;
    TextField angleInDegrees, sinAlpha, cosAlpha;

    private Scene makeTestScene() {
        slider = Builders.slider(-2.0 * PI, 2 * PI, 0.0).init(s -> {
            s.setLabelFormatter(Converters.makeStrConverter(
                    degsToRadians.compose(Double::parseDouble),
                    radsToDegrees.andThen("%.1f"::formatted)));
        });
        angleInDegrees = Builders.textField("").bind(slider.valueProperty(), TextInputControl::textProperty,
                doubleValue.andThen(radsToDegrees).andThen("%.1f"::formatted)).get();
        sinAlpha = Builders.textField("").bind(slider.valueProperty(), TextInputControl::textProperty,
                doubleValue.andThen(Math::sin).andThen("%.2f"::formatted)).get();
        cosAlpha = Builders.textField("").bind(slider.valueProperty(), TextInputControl::textProperty,
                doubleValue.andThen(Math::cos).andThen("%.2f"::formatted)).get();
        var vbox = Builders.vBox().addChild(
                Builders.vBox()
                        .addChild(Builders.labeledRow("Angle in Degrees").addChild(angleInDegrees))
                        .addChild(Builders.labeledRow("Sin(alpha)").addChild(sinAlpha))
                        .addChild(Builders.labeledRow("Cos(alpha)").addChild(cosAlpha))
        ).addChild(slider).get();

        return new Scene(vbox, 720, 100);
    }
    @Start
    public void start(Stage stage) {
        stage.setScene(makeTestScene());
        stage.show();
    }

    @Test
    public void testValues(FxRobot robot) {
        final double radFor30degs = PI/6;
        final double radFor60degs = PI/3;


        slider.setValue(radFor30degs);
        Assertions.assertEquals("%.1f".formatted(30.0), angleInDegrees.getText());
        Assertions.assertEquals("%.2f".formatted(Math.sin(radFor30degs)), sinAlpha.getText());
        Assertions.assertEquals("%.2f".formatted(Math.cos(radFor30degs)), cosAlpha.getText());

        slider.setValue(radFor60degs);
        Assertions.assertEquals("%.1f".formatted(60.0), angleInDegrees.getText());
        Assertions.assertEquals("%.2f".formatted(Math.sin(radFor60degs)), sinAlpha.getText());
        Assertions.assertEquals("%.2f".formatted(Math.cos(radFor60degs)), cosAlpha.getText());
    }
}
