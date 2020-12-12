package com.jijith.alexa.vo

import android.os.Parcel
import android.os.Parcelable

data class WikiDisplayCard(
    val image: Image?,
    val textField: String?,
    val title: Title?,
    val token: String?,
    val type: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Image::class.java.classLoader),
        parcel.readString(),
        parcel.readParcelable(Title::class.java.classLoader),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(image, flags)
        parcel.writeString(textField)
        parcel.writeParcelable(title, flags)
        parcel.writeString(token)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WikiDisplayCard> {
        override fun createFromParcel(parcel: Parcel): WikiDisplayCard {
            return WikiDisplayCard(parcel)
        }

        override fun newArray(size: Int): Array<WikiDisplayCard?> {
            return arrayOfNulls(size)
        }
    }
}

data class Image(
    val sources: List<Source>?
): Parcelable {
    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(Source)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(sources)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Image> {
        override fun createFromParcel(parcel: Parcel): Image {
            return Image(parcel)
        }

        override fun newArray(size: Int): Array<Image?> {
            return arrayOfNulls(size)
        }
    }
}

data class Title(
    val mainTitle: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeString(mainTitle)
    }

    companion object CREATOR : Parcelable.Creator<Title> {
        override fun createFromParcel(parcel: Parcel): Title {
            return Title(parcel)
        }

        override fun newArray(size: Int): Array<Title?> {
            return arrayOfNulls(size)
        }
    }
}

data class Source(
    val size: String?,
    val url: String?
): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel?, p1: Int) {
        parcel?.writeString(size)
        parcel?.writeString(url)
    }

    companion object CREATOR : Parcelable.Creator<Source> {
        override fun createFromParcel(parcel: Parcel): Source {
            return Source(parcel)
        }

        override fun newArray(size: Int): Array<Source?> {
            return arrayOfNulls(size)
        }
    }
}