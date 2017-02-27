
东农助手 - 东北农业大学教务处第三方Android客户端
===================================

## 注意
不支持 Android 4.4 及以下版本

## 下载地址

- [ownCloud](https://light.dattodrive.com/index.php/s/exSSWFRV3XaIK6f)
- [Github release](https://github.com/V20Ea/Neau-Assistant/releases/download/v1.01/com.neau.assistant_v1.01.apk)

## 功能实现
- [x] 学籍信息
- [x] 成绩
- [ ] 本学期成绩
- [ ] 本学期课表
- [x] 一键评教
- [x] 通知公告
- [ ] 主题切换

## 开发进度

### v_1.01
> * Forked from [jin123d/URP](https://github.com/jin123d/URP)，THANKS for him！
> * 适配东农教务处接口
> * 增加考务信息功能

---
### v_1.0
> * （优化）大幅度开始重构

### v_0.99
> * （更新）一键评教功能更新为期末评教
> * （更新）评估内容取消小尾巴，内容为：good
> * （修复）不及格成绩获取页面加入了未评教信息验证，防止App崩溃
> * （优化）自主实践查询页面加入提示信息，提高友好度
> * （修复）Activity跳转闪屏的问题
> * （更新）提示框样式修改

### v_0.98
> * （修复）修复教务处网站改版后客户端无法获取到通知公告的BUG
> * （增加）增加了自动更新功能

### v_0.97
> * （修复）修复成绩界面在API17下错乱的BUG
> * （优化）优化一部分Dialog

### v_0.96
> * （优化）优化查询线程

### v_0.95
> * （增加）增加侧边栏
> * （修复）修复一个因为逻辑错误导致无限重复登录的bug
> * （增加）关于界面

### v_0.92
> * （调整）主界面UI,根据material design
> * （调整）更换签名为正式版签名

### v_0.91

> * （修复）修复评教内容中文乱码的bug，
> * （增加）增加关于界面

### v_0.9
> * （增加）增加一键评教功能:10s就可以评估完成20门以上科目

### v_0.8
> * （修复）登录时获取数据超时处理

### v_0.7
> * （增加）本学期课表（尚未完成）
> * （增加）通知公告逻辑完成，UI需要继续调整

### v_0.6
> * （修改）主页面改为列表形式，加入图标
> * （修改）调整登录页面UI，调整登录button
> * （增加）成绩查询中加入不及格成绩的统计信息（尚未通过、曾经未通过）
> * （修改）登录页面的Toast一部分修改为Snackbar

### v_0.5
> * （增加）不及格成绩完成（尚不及格、曾不及格）
> * （修改）修改tabIndicatorColor和colorAccent
> * （修改）调整成绩查询页面UI

### v_0.4
> * （增加）取消Actionbar,替换为Toolbar
> * （增加）加入material design颜色处理
> * （增加）成绩查询中采用TabLayout 制作选项卡，选项卡制作完成，不及格成绩未完成
> * （增加）头部加入AppBarLayout，按照Google规范

### v_0.3
> * （增加）全局加入请求超时处理，防止卡死
> * （修复）修复一些可能导致崩溃的bug
> * （优化）自主实践查询中字体过大导致显示不完整的bug
> * （修复）全局ProgressBarDialog提示修正
> * （修复）进入APP后验证码获取时修复progressbar的正常显示
> * （增加）增加登录失败提示信息（如密码错误、验证码错误等）

### v_0.2
> * （增加）获取验证码时将验证码输入框置空
> * （更新）界面主题更换为DarkActionbar
> * （增加）退出登录加入提示
> * （增加）增加父级Activity
> * （增加）双击退出，防止误操作

### v_0.1
> * 登录urp
> * 获取及格成绩、个人信息、自主实践、学分绩点
> * 记住密码、自动登录功能
> * 退出登录功能
> * 及格成绩和自主实践采用baseAdapter