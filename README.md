# Actions - Mars/xlog

利用 Github Actions 编译 [Tencent Mars xlog](https://github.com/Tencent/mars) 的 Android Libs，并打包发布到 Github Packages 的 Maven 仓库。

🚩 特别说明：

- 想改 CPU 架构的修改[这一行](https://github.com/ichenhe/Actions-Mars/blob/2f7a7e759625d657f9d9d8c025e18cd2c34533ac/.github/workflows/build.yml#L41)。
- 本仓库的代码是打包为 AAR 的 wrapper 工程，编译所需的 mars 源码总是动态地拉取。

## 懒人看这

你可以直接使用本仓库编译好的 AAR，此工件为 Tencent/Mars 源码 AS IS 编译，未做修改。

1. 添加 maven 仓库地址

```groovy
maven {
    url "https://maven.pkg.github.com/ichenhe/Actions-Mars"
    credentials{
        username("chenhe-pub")
        password("&#103;hp_iEietheghA8ocZN0vCEvb6qCCx0xsU4YMFBf")
    }
}
```

>方便起见，工件直接托管在了 Github Packages，由于[极其反人类的设计](https://github.community/t/download-from-github-package-registry-without-authentication/14407/111)，哪怕是公开的仓库，也不能匿名访问，必须提供一个具有 `read:packages` 权限的 Github 账户 token 才可以。
>
>这里提供了一个我自己的 Machine Account，但建议你替换成自己的 token 以免受到我个人账户状态变更的影响。

2. 添加依赖

```groovy
implementation("me.chenhe:mars-xlog:$version")
```

最新编译的版本号看[这里](https://github.com/ichenhe/Actions-Mars/packages/925085)。

**编译说明**

从 Tencent/Mars 拉取 master 分支编译，然后打包为 aar。版本号与 Mars 版本号无关，也不是语义化的，只是简单地递增。（因为我搞不清楚 Mars 的发版周期）

编译了 `armeabi-v7a` `arm64-v8a` `x86` 三个 CPU 架构。

仅编译了 xlog，不是 mars 全家桶。

## 自己编译

1. clone 本仓库
2. 进入 Actions 页面 -> `Build`
3. 点击 run workflow，输入一个版本号。

> 💡Tips 除了发布到 Maven，也会把编译后的 so 以及对应的 java 代码打包上传到下面的 Artifacts 区域，默认保存三天。

## 需要改源码？

自己修改 `.github/workflows/` 下面的文件吧 [doge]

💡小提示：利用 [debugger-action](https://github.com/csexton/debugger-action) 可以允许你连入 actions runner 的终端。

