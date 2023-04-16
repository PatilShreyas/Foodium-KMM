![](/readme-media/graphic.png)

# 🍲 Foodium (Kotlin Multiplatform Mobile)

Foodium is a simple and sample _mobile application_ built to demonstrate the use of
_**Kotlin Multiplatform Mobile**_ for developing _Android and iOS_ applications
using **Jetpack Compose** 🚀.

| Platforms | ![](https://img.shields.io/badge/Android-black.svg?style=for-the-badge&logo=android) ![](https://img.shields.io/badge/iOS-black.svg?style=for-the-badge&logo=apple)  |
|-----------|---|
| Status    | [![Build](https://github.com/PatilShreyas/Foodium-KMM/actions/workflows/build.yml/badge.svg)](https://github.com/PatilShreyas/Foodium-KMM/actions/workflows/build.yml)  |


## About 

It simply loads posts data from API and displays them on the UI. The network API is a dummy (fixed)
response which is _statically hosted 
[here](https://patilshreyas.github.io/DummyFoodiumApi/api/posts/)_. The concept of this app is 
originally taken from [this project](https://github.com/PatilShreyas/Foodium/).

### 📱 Preview

Currently, the app looks like this on the both platforms:

#### ▶️ Android

https://user-images.githubusercontent.com/19620536/232288166-5ca48ebc-b36f-43f3-b759-471834b31c2f.mp4

#### ▶️ iOS

https://user-images.githubusercontent.com/19620536/232288185-fff651ef-1acc-4b68-92e7-d88a0a4e1f4a.mp4

---

## Built with 

- [Kotlin](kotlinlang.org): Programming language
- [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html): For building multi-platform applications in the single codebase.
- [Jetpack/JetBrains Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/): For a shared UI between multi-platforms i.e. Android and iOS in this project.
- Kotlinx
  - [Coroutines](https://github.com/Kotlin/kotlinx.coroutines): For multithreading
  - [Serialization](https://github.com/Kotlin/kotlinx.serialization): For JSON serialization/deserailization
- [Ktor Client](https://github.com/ktorio/ktor): Performing HTTP requests, Creating image loading utility for iOS module.
- [Coil](https://github.com/coil-kt/coil): Image loading for Android
- [Mutekt](https://github.com/PatilShreyas/mutekt): For UI state management 


### TODOs

Currently, it only shows a one Home screen with listing of posts. The following features will be
integrated in the app and _Work is in Progress..._

#### ✅ Completed
- [x] Home screen UI
- [x] Load posts from network
- [x] Load images from network

#### 🚧 Work in progress
- [ ] Details screen UI
- [ ] Offline capability: Store posts data in local cache (Use SQLDelight)
- [ ] Use navigation framework
- [ ] Switch for dark/light theme.

## Contribute

If you want to contribute to this library, you're always welcome!
See [Contributing Guidelines](CONTRIBUTING.md).

## Discuss 💬

Have any questions, doubts or want to present your opinions, views? You're always welcome.
You can [start discussions](https://github.com/PatilShreyas/Foodium-KMM/discussions).

## Acknowledgements 

- [JetBrains/compose-multiplatform-ios-android-template](https://github.com/JetBrains/compose-multiplatform-ios-android-template#readme):
  For Starter template
- [google/accompanist](https://github.com/google/accompanist): For placeholder (shimmer animation)
  APIs

## License

```
Copyright 2023 Shreyas Patil

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
