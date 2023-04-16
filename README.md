![](/readme-media/graphic.png)

# 🍲 Foodium (Kotlin Multiplatform Mobile)

Foodium is a simple and sample _mobile application_ built to demonstrate the use of
_**Kotlin Multiplatform Mobile**_ for developing _Android and iOS_ applications
using **Jetpack Compose** 🚀.

|           |   |
|-----------|---|
| Status    | [![Build](https://github.com/PatilShreyas/Foodium-KMM/actions/workflows/build.yml/badge.svg)](https://github.com/PatilShreyas/Foodium-KMM/actions/workflows/build.yml)  |
| Stack     | ![](https://img.shields.io/badge/Kotlin%20Multiplatform-7F52FF.svg?style=flat-square&logo=kotlin&logoColor=white) ![](https://img.shields.io/badge/Jetpack%20Compose-4285F4.svg?style=flat-square&logo=jetpack%20compose&logoColor=white) ![](https://img.shields.io/badge/Ktor%20HTTP%20Client-orange.svg?style=flat-square&logo=jetbrains&logoColor=white)  |
| Platforms | ![](https://img.shields.io/badge/Android-black.svg?style=for-the-badge&logo=android) ![](https://img.shields.io/badge/iOS-black.svg?style=for-the-badge&logo=apple)  |

## About 

It simply loads posts data from API and displays them on the UI. The network API is a dummy (fixed)
response which is _statically hosted 
[here](https://patilshreyas.github.io/DummyFoodiumApi/api/posts/)_. The concept of this app is 
originally taken from [this project](https://github.com/PatilShreyas/Foodium/).

Currently, it only shows a one Home screen with listing of posts. The following features will be
integrated in the app and _Work is in Progress..._

### TODOs

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