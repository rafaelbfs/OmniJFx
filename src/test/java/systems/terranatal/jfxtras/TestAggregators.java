package systems.terranatal.jfxtras;

import systems.terranatal.jfxtras.datautils.Converters;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.function.Function;

import static java.lang.Math.PI;

@ExtendWith(ApplicationExtension.class)
public class TestAggregators {
    private final Function<Double, Double> degsToRadians = degs -> degs * PI/180.0;
    private final Function<Double, Double> radsToDegrees = rads -> rads * 180.0/PI;

    private final Function<Number, Double> doubleValue = Number::doubleValue;
    Slider slider;
    TextField angleInDegrees, sinAlpha, cosAlpha;

    private Scene makeTestScene() {
        slider = Init.slider(-2.0 * PI, 2 * PI, 0.0).with(s -> {
            s.setLabelFormatter(Converters.makeStrConverter(
                    degsToRadians.compose(Double::parseDouble),
                    radsToDegrees.andThen("%.1f"::formatted)));
        });
        angleInDegrees = Init.textField().with(tf -> tf.textProperty()
                .bind(slider.valueProperty().map(
                        doubleValue.andThen(radsToDegrees)
                        .andThen("%.1f"::formatted)))
        );
        sinAlpha = Init.textField().with(tf -> tf.textProperty()
                .bind(slider.valueProperty().map(
                        doubleValue.andThen(Math::sin).andThen("%.2f"::formatted)))
        );
        cosAlpha = Init.textField().with(tf -> tf.textProperty()
                .bind(slider.valueProperty().map(
                        doubleValue.andThen(Math::cos).andThen("%.2f"::formatted)))
        );
        var vbox = Init.vbox().aggregator().addChild(
                Init.hbox().aggregator().addChild(
                        Init.withLabelOnLeft("Angle in degrees").addChild(angleInDegrees).get()
                ).addChild(
                        Init.withLabelOnLeft("Sin(x)").addChild(sinAlpha).get()
                ).addChild(
                        Init.withLabelOnLeft("Cos(x)").addChild(cosAlpha).get()
                ).get()
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
