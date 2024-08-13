package systems.terranatal.omnijfx.kfx.properties

import javafx.beans.property.Property
import kotlin.reflect.KProperty

operator fun <T> Property<T>.getValue(thisRef: Any?, property: KProperty<*>): T? = value

operator fun <T> Property<T>.setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
  setValue(value)
}