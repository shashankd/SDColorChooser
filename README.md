## SDColorChooser Android Library
    SDColorChooser is a chooser ViewGroup which returns selected imageview/color index

![SDColorChooser] (https://raw.github.com/shashankd/SDColorChooser/master/screenshot.png)

We can customise background color and other dimension properties.
Pass the list of drawables (ImageDrawables or ColorDrawables)

# SDColorChooser

### Gradle
```
dependencies {
    ...
    implementation 'com.sdcolorchooser:sdcolorchooser:1.0.1'
}
```

##### Using SDColorChooser in XML
```xml
<com.sdcolorchooser.SDColorChooser
        android:id="@+id/sdColorChooser"
        android:layout_width="300dp"
        android:layout_height="300dp"
        android:layout_alignParentTop="true"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="100dp"
        custom:backgroundColor="@color/colorAccent"
        custom:pivotHeight="@dimen/pivot_triangle_height"
        custom:shadowColor="@android:color/darker_gray"
        custom:shadowWidth="@dimen/divider_width" />
```



# SDCircleImageView

### Gradle
```
dependencies {
    ...
    implementation 'com.sdcircleimageview:sdcircleimageview:1.0.1'
}
```

##### Using SDCircleImageView in XML
```xml
    <com.sdcircleimageview.SDCircleImageView
        android:id="@+id/sdCircleView"
        android:layout_width="250dp"
        android:layout_height="250dp"
        android:layout_below="@id/sdColorChooser"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="20dp"
        custom:borderColor="@color/colorAccent"
        custom:borderWidth="@dimen/ring_width"
        custom:showBorder="true" />
```

## Setting Image bitmap to SDCircleImageView in Java
```
    sdCircleView.setInnerBitmapImage(bitmap);
```