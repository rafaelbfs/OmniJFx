package systems.terranatal.omnijfx.kfx.properties

import javafx.beans.property.ObjectPropertyBase
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class FxProperty<T>(initialValue: T?,
                    private val bean: Any? = null,
                    private val name: String = ""
): ObjectPropertyBase<T>(initialValue), ReadWriteProperty<Any?, T?> {

  override fun getBean() = bean

  override fun getName() = name

  override fun getValue(thisRef: Any?, property: KProperty<*>): T? {
    return get()
  }

  override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
    set(value)
  }
}