# 2020/07/17 ［Android］AndroidX Preference Library を使って設定画面を作成する

# はじめに

Android では Preference Library を使うことで簡単に設定画面を作ることができるようになっています。今回は Preference Library をどのように使えるのか簡単にまとめたいと思います。

<a href="https://gyazo.com/822ec1fcc15e445f33f984b22c54d186"><img src="https://i.gyazo.com/822ec1fcc15e445f33f984b22c54d186.gif" alt="Image from Gyazo" width="256"/></a>

# インストール

Preference Library を build.gradle(app) の依存関係に追加します。追加しなくてもコード補完候補にクラス名称が出てくることがありますが古いライブラリが参照されて上手く動作しないので依存関係は追加したほうが良いです。

```groovy
dependencies {
    def preference_version = "1.1.1"
    implementation "androidx.preference:preference-ktx:$preference_version"
}
```


# 設定画面の作り方

Preference Library を利用して設定画面を作成するときには次の手順で作成します。

1. 設定画面を XML ファイルに定義する。
2. 設定画面（XMLファイル）を表示する PreferenceFragmentCompat を作成する。
3. PreferenceFragmentCompat を Activity で表示してやる

## 1. 設定画面を XML ファイルに定義する。

Preference Library では XMLファイルを res/xml フォルダに作成して設定画面を定義します。Preference Library では大まかに 3 つの要素が用意されており、これらの要素を配置することで設定画面を定義していきます。

| 名称               | 解説                                                     |
| ------------------ | -------------------------------------------------------- |
| PreferenceScreen   | 複数の Preference を格納するためのコンテナでルートの要素 |
| PreferenceCategory | 複数の Preference をカテゴリ別にするための要素           |
| Preference         | 設定項目を定義するための要素                             |

また PreferenceCategory や Preferenceには attribute を定義することで見た目を変更します。代表的なものとしては次の3つがあります。attribute に関しては Preference の種類ごとに異なるので公式ドキュメントを確認してみてください。

| 名称        | 解説                   |
| ----------- | ---------------------- |
| app:icon    | 項目に表示するアイコン |
| app:title   | 項目に表示するタイトル |
| app:summary | 項目に表示するテキスト |

```xml
<?xml version="1.0" encoding="utf-8"?>
<?xml version="1.0" encoding="utf-8"?>
<PreferenceScreen xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <PreferenceCategory
        app:key="preferenceCategory"
        app:icon="@mipmap/ic_launcher"
        app:title="Category"
        app:summary="Category Summary">

        <CheckBoxPreference
            app:icon="@mipmap/ic_launcher"
            app:key="checkBoxPreference"
            app:summary="CheckBox XXXX-XXXX-XXXX"
            app:title="CheckBox"/>
    </PreferenceCategory>

</PreferenceScreen>
```

このように Preference Library を使った設定画面の作成は通常のレイアウトファイルを記述するのと同じ要領で作成できるようになっています。もちろん次のように作成した設定画面は Android Studio で Preview もできるようになっています。

<a href="https://gyazo.com/49820a8aeab0eb381d49ac28e0e3069b"><img src="https://i.gyazo.com/49820a8aeab0eb381d49ac28e0e3069b.png" alt="Image from Gyazo" width="518"/></a>

## 2. 設定画面(XMLファイル)を表示する PreferenceFragmentCompat を作成する。

あとは PreferenceFragmentCompat というクラスを定義して XML ファイルを読み込むようにします。次のように onCreatePreferences を override して setPreferencesFromResource にて XML ファイルを指定してやればOKです。

```kotlin
class ParentPreferenceFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.parent_preferences, rootKey)
    }
}
```

## 3. PrerenceFragmentCompat を Activiy で表示してやる。

あとは作成した PreferenceFragmentCompat を通常の Fragment と同じように Activity に配置してやるだけです。あとはアプリを起動してやれば XML ファイルに定義した設定画面が表示され変更できるようになります。

```kotlin
class MainActivity : AppCompatActivity(R.layout.activity_main)
```

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <androidx.fragment.app.FragmentContainerView
        android:id="@+id/fragment_container"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:name="jp.kaleidot725.sample.setting.ParentPreferenceFragment"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

<a href="https://gyazo.com/43c903339094ec17f79eaf8a27632a61"><img src="https://i.gyazo.com/43c903339094ec17f79eaf8a27632a61.gif" alt="Image from Gyazo" width="256"/></a>
# 設定を読み込む

Preference Library で用意された　Preference には変更した設定値を Shared Preference に保存する機能が実装されています。Preference の attirbute の app:key を定義するだけでこの機能は有効になり、指定したキーで SharedPreference に設定値が保存されます。

```xml
<?xml version="1.0" encoding="utf-8"?>
<PreferenceScreen xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <PreferenceCategory
        app:key="preferenceCategory"
        app:icon="@mipmap/ic_launcher"
        app:title="Category"
        app:summary="Category Summary">

        <CheckBoxPreference
            app:icon="@mipmap/ic_launcher"
            app:key="checkBoxPreference"
            app:summary="CheckBox XXXX-XXXX-XXXX"
            app:title="CheckBox"/>
    </PreferenceCategory>

</PreferenceScreen>
```

また保存された設定値は設定画面を開いたときに自動的に読み込まれるようになっています。Preference Library を使った設定値の保存はものすごく簡単でライブラリ側が全部やってくれるようになっています。

<a href="https://gyazo.com/822ec1fcc15e445f33f984b22c54d186"><img src="https://i.gyazo.com/822ec1fcc15e445f33f984b22c54d186.gif" alt="Image from Gyazo" width="256"/></a>

SharedPreference に保存されているので設定値の読み込みも簡単です。次のコードで SharedPreference を取得して、Preferenceに指定した Key を指定して値を取得すればOKです。

```kotlin
val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
val checkStatus = sharedPreferences.getBoolean("checkBoxPreference", false)
Log.v("TAG", "CheckStatus $checkStatus")
```

# おわりに

- AndroidX Preference Library を使うと簡単に設定画面を作れる。
- PreferenceScreen は PreferenceCategory や Preference を格納するための要素
- PreferenceCategory は Preference をカテゴリ別にまとめるための要素
- Preference は設定項目の要素で CheckBox を持ったものや EditText を持つものがある。
- Preference には icon や title、 summary などの attribute が用意されていて見た目をカスタマイズできる
- Preference に key を設定すると SharedPreference にそのキーで設定値が保存されるようになる。保存された設定値は Preference を再表示した際に自動的に読み込まれる。保存された設定値は 通常の SharedPreference の読み込み処理で読み込むことができる。

# 参考文献

- [設定 | Android Developers](https://developer.android.com/guide/topics/ui/settings)
- [androidx.preference](https://developer.android.com/jetpack/androidx/releases/preference#version_111_3) 
- [kaleidot-725/preference](https://github.com/kaleidot725-android/preference)