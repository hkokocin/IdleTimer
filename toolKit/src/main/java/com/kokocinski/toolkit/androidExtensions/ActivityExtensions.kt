package com.kokocinski.toolkit.androidExtensions

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import java.io.Serializable
import kotlin.reflect.KClass

fun <T : Activity> Activity.startForResult(
        type: KClass<T>,
        requestCode: Int,
        init: Intent.() -> Unit = {}
) {
    val intent = Intent(this, type.java)
    intent.init()
    startActivityForResult(intent, requestCode)
}

fun <T : Any> Activity.extra(activity: Activity, name: String, type: KClass<T>) =
        getExtra(activity.intent, name, type)

fun <T : Any> Activity.extra(activity: Activity, name: String, default: T, type: KClass<T>) =
        getExtra(activity.intent, name, type) ?: default

@Suppress("UNCHECKED_CAST")
fun <T : Any> getExtra(intent: Intent, name: String, type: KClass<T>): T? = when (type) {
    Char::class                -> intent.getCharExtra(name, ' ') as T?
    Array<Char>::class         -> intent.getCharArrayExtra(name).toTypedArray() as T?
    CharArray::class           -> intent.getCharArrayExtra(name) as T?
    String::class              -> intent.getStringExtra(name) as T?
    Array<String>::class       -> intent.getStringArrayExtra(name) as T?
    CharSequence::class        -> intent.getCharSequenceExtra(name) as T?
    Array<CharSequence>::class -> intent.getCharSequenceArrayExtra(name) as T?
    Short::class               -> intent.getShortExtra(name, 0) as T?
    Array<Short>::class        -> intent.getShortArrayExtra(name).toTypedArray() as T?
    ShortArray::class          -> intent.getShortArrayExtra(name) as T?
    Int::class                 -> intent.getIntExtra(name, 0) as T?
    Array<Int>::class          -> intent.getIntArrayExtra(name).toTypedArray() as T?
    IntArray::class            -> intent.getIntArrayExtra(name) as T?
    Long::class                -> intent.getLongExtra(name, 0) as T?
    Array<Long>::class         -> intent.getLongArrayExtra(name).toTypedArray() as T?
    LongArray::class           -> intent.getLongArrayExtra(name) as T?
    Double::class              -> intent.getDoubleExtra(name, 0.0) as T?
    Array<Double>::class       -> intent.getDoubleArrayExtra(name).toTypedArray() as T?
    DoubleArray::class         -> intent.getDoubleArrayExtra(name) as T?
    Float::class               -> intent.getFloatExtra(name, 0f) as T?
    Array<Float>::class        -> intent.getFloatArrayExtra(name).toTypedArray() as T?
    FloatArray::class          -> intent.getFloatArrayExtra(name) as T?
    Boolean::class             -> intent.getBooleanExtra(name, false) as T?
    Array<Boolean>::class      -> intent.getBooleanArrayExtra(name).toTypedArray() as T?
    BooleanArray::class        -> intent.getBooleanArrayExtra(name) as T?
    Byte::class                -> intent.getByteExtra(name, 0) as T?
    Array<Byte>::class         -> intent.getByteArrayExtra(name).toTypedArray() as T?
    ByteArray::class           -> intent.getByteArrayExtra(name) as T?
    Serializable::class        -> intent.getSerializableExtra(name) as T?
    Bundle::class              -> intent.getBundleExtra(name) as T?
    else                       -> throw  IllegalArgumentException("cannot get extra of unknown type ${type.java.name}")
}