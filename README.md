# NProgress
> Circle ProgressBar
> Easy to use

# Method to set Attributes
- setCircleBaseColor();
- setCircleProgressColor();
- setTextColor();
- setTextSize();
- setMax();
- setProgress();
- setStrokeWidth();
- setText();
- setProgressValueVisibility();
- setProgressCapRound();
- setBlockBackgroundColor();
- setTextPadding(();
  
# How to use

### 1. Gradle 
>Gradle (Top-level).

```gradle
...
buildscript {
    repositories {
        jcenter() 
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:2.1.2'
    }
}
allprojects {
    repositories {
        jcenter()
        maven { url 'https://jitpack.io'}
    }
}
...
```


>Gradle (On your Module:App)

```gradle
...
dependencies {
    compile 'com.github.opannapo:NProgress:1.0'
} 
...
```

### 2. XML Layout
```xml
<napodev.nprogresslib.NProgressLib
    android:layout_width="150dp"
    android:layout_height="150dp"
    android:layout_margin="10dp"
    android:background="#00000000"
    custom:block_color="#f429a3"
    custom:circle_base_color="#454545"
    custom:circle_progress_color="#0df0f4"
    custom:max="100"
    custom:progress="100"
    custom:progressValueVisibility="true"
    custom:stroke_width="20dp"
    custom:text="STEP-1"
    custom:text_color="#000"
    custom:text_size="12dp"/>
```

### 3. Java
```Java
public class MainActivity extends AppCompatActivity { 
    NProgressLib progress1; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progress1 = (NProgressLib) findViewById(R.id.progress1);
        progress1.setProgress(70);//Set Progress Value 70%
        progress1.setCircleProgressColor(Color.parseColor("#00ff00")); // Progress COlor
        progress1.setText("Step1"); //Label
        progress1.setTextSize(12);// TextSize
        progress1.setProgressValueVisibility(true);//Visible Progress Value on Center Frame
        progress1.setCircleBaseColor(Color.parseColor("#aaaaaa"));//Base Progress Color
        progress1.setProgressCapRound(true);//style rounded
        progress1.setStrokeWidth(10);//progressbar width
        progress1.setTextPadding(5);//text Padding
    }
}
``` 

### 4. Output
Youtube : https://youtu.be/NgM9-3_7ElM
