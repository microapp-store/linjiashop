module.exports = {
    title: '邻家小铺',
    description: '使用邻家小铺搭建自己的全平台商城系统',
    base: '/linjiashop/',
    head: [
        ['link', { rel: 'shortcut icon', type: "image/x-icon", href: './favicon.ico' }]
    ],
    evergreen: true,
    editLinkText:'在 GitHub 上编辑此页',
    port: 8081,
    ga: 'UA-71886989-13',
    themeConfig: {
        repo: 'microapp-store/linjiashop',
        docsDir: 'doc',
        editLinks: true,
        editLinkText: '编辑此页面！',
        nav: [
            {text: '文档', link: '/'},
            {text: '更新日志',link:'/base/changelog'},
            {text: '资源',link:'/base/resource'},
            {text: 'Gitee', link: 'https://gitee.com/microapp/linjiashop'}
        ],
        sidebar: [
            {
                title: '准备',
                collapsable: false,
                children: [
                    '/base/preface',
                    '/base/jdkAndMaven',
                    '/base/modules'
                ]
            },
            {
                title: '20分钟将本项目跑起来',
                collapsable: false,
                children: [
                    '/quickstart/quickstart',
                    '/quickstart/clone',
                    '/quickstart/initDb',
                    '/quickstart/config',
                    '/quickstart/startup'
                ]
            },
            {
                title: '二次开发',
                collapsable: false,
                children: [
                    '/develop/api',
                    'develop/banner',
                    '/develop/attribute'
                ]
            },

            {
                title: '用户使用手册',
                collapsable: false,
                children: [
                    '/useage/useage'
                ]
            },
            {
                title: '其他',
                collapsable: false,
                children:[
                    '/other/faq'
                ]
            }
        ]

    }
}
