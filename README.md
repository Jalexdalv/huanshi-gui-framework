# 基于Java Swing的Windows桌面端程序MVC框架
[![license](https://img.shields.io/github/license/mashape/apistatus.svg?style=for-the-badge)](LICENSE)
## 介绍
本框架是对**Java Swing**的二次封装及拓展，**仍在持续更新中......**
+ 配置文件和代码分离，使项目结构更加清晰
+ 美化组件和界面，风格更具现代化
+ 将数据/视图/控制完全分离，代码耦合度更低，使MVC架构更加松散
## 结构
|  层次   | 功能  |
|  ----  | ----  |
| **common**  | 常用工具类、注解定义、异常定义、基础抽象模型等 |
| **model**  | 组件数据模型，定义组件数据 |
| **view**  | 组件显示层，定义组件位置、布局等 |
| **controller**  | 组件控制层，交互逻辑写在这一层 |
| **application**  | 应用启动层，定义启动时的逻辑 |
| **core**  | 核心层，主要有三大作用(项目扫描、组件加载、程序启动) |
## 使用方法
待更新  
## 例子
待更新  
## 已知BUG
+ 在系统缩放情况下，某些组件以及图片会变得模糊
## 更新记录
+ 加入了IconTextField与IconPasswordField组件
+ 重写了splash启动图，现在只允许设置一张图片
+ 加入了启动配置文件，可以设置开发模式与发布模式
+ 改正了组件渲染时的一些BUG
+ 优化了Icon组件的渲染机制
+ 加入了CustomFrame窗口组件
+ 加入了Panel背景图片选项
+ 加入了Frame是否允许最大化选项
+ 加入了自底向上repaint机制
+ 修改了splash图片渲染机制
