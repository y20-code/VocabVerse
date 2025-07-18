<div align="center">
  <h1 style="font-size: 3em; color: #2c3e50; margin: 0;">VocabVerse</h1>
  <p style="font-size: 1.1em; color: #6c757d; margin-top: 10px;">一个为学生、家长、老师三方协同设计的、数据驱动的在线智能背单词平台</p>
  <p>
    <a href="[你的Live Demo链接，暂时留空]" target="_blank">🚀 Live Demo</a>   |  
    <a href="[你的后端仓库链接]" target="_blank">📦 后端源码</a>   |  
    <a href="[你的前端仓库链接]" target="_blank">🎨 前端源码</a>
  </p>
</div>

---

<div align="center">
  <p><i>[这里未来放一张项目动态GIF演示图]</i></p>
</div>

---

## ✨ 项目亮点

*   🎓 **个性化学习路径 (学生端):** 基于艾宾浩斯遗忘曲线智能推荐复习内容。
*   👨‍🏫 **教学管理闭环 (老师端):** 轻松创建班级、指派任务，实时掌握学情。
*   📈 **无忧学习监督 (家长端):** 通过 ECharts 图表化数据报告，直观了解学习进度。

---

## 🛠️ 技术栈

| 分类          | 技术                                                    |
| :------------ | :------------------------------------------------------ |
| **后端**      | Spring Boot, Spring Security (JWT), MyBatis-Plus, Maven |
| **前端**      | Vue 3, Vue Router, Pinia, Axios, Element Plus, ECharts  |
| **数据库**    | MySQL 8.0, Redis (缓存与分布式会话)                     |
| **开发&部署** | Git, Docker, IntelliJ IDEA, VS Code, Postman            |

---

## 🤔 遇到的挑战与解决方案

在开发本项目期间，我遇到了诸多挑战，从环境配置、核心功能实现到架构设计，每一次解决问题的过程都让我对技术有了更深的理解。

我已将这些详细的思考过程、解决方案以及技术复盘，整理成了项目的 **[深度思考与实践 Wiki](https://github.com/y20-code/VocabVerse/wiki)**。

**部分主题包括：**

*   `Spring Boot 启动时数据源连接失败的排查与解决`
*   `基于 Spring IoC 与 AOP 原则进行服务解耦的设计思考`
*   `前后端分离开发中的“契约”建立与完整数据链路分析`

欢迎查阅！



## 🚀 快速开始
### 1. 环境准备
*   确保已安装 JDK 17+, Maven 3.6+, MySQL 8.0+, Redis.
*   在你的 MySQL 中创建一个名为 `vocabverse` 的数据库。

### 2. 初始化数据库
*   执行项目根目录下 `/sql/init.sql` 文件中的 SQL 脚本，创建项目所需的表结构。

### 3. 配置后端
*   修改 `backend/src/main/resources/application.yml` 文件，更新数据库和 Redis 的连接信息（URL, username, password）。
*   **重要：** 确保已开启 MyBatis-Plus 的下划线转驼峰映射：
  ```yaml
  mybatis-plus:
    configuration:
      map-underscore-to-camel-case: true
  ```
### 4. 启动服务
*   **后端:** 进入 `backend` 目录，运行 `mvn spring-boot:run`。
*   **前端:** 进入 `frontend` 目录，运行 `npm install && npm run dev`。

### 5. API 文档
*   本项目使用 **Swagger (OpenAPI 3)** 进行接口管理。
*   启动后端服务后，访问 [http://localhost:8088/swagger-ui.html](http://localhost:8080/swagger-ui.html) 即可在线查看、调试所有API。
---

## 📸 项目截图

*(待补充，完成各页面功能后截图更新)*