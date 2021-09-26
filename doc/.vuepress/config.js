module.exports = {
    title: '邻家小铺',
    description: '使用邻家小铺搭建自己的全平台商城系统',
    base: '/linjiashop/',
    head: [
        ['link', {rel: 'shortcut icon', type: "image/x-icon", href: './favicon.ico'}],
        ['script', {}, `
            var _hmt = _hmt || [];
            (function() {
              var hm = document.createElement("script");
              hm.src = "https://hm.baidu.com/hm.js?9d53390a6ba903e20fabf2e207b3e9bb";
              var s = document.getElementsByTagName("script")[0];
              s.parentNode.insertBefore(hm, s);
            })();
        `
        ]
    ],
    evergreen: true,
    editLinkText: '在 GitHub 上编辑此页',
    port: 8090,
    ga: 'UA-71886989-13',
    themeConfig: {
        repo: 'microapp-store/linjiashop',
        docsDir: 'doc',
        editLinks: true,
        editLinkText: '编辑此页面！',
        nav: [
            {text: '指南', link: '/guide/base/preface'},
            {
                text: '生态系统',
                items: [
                    {
                        text: "项目", items: [
                            {text: 'linjiashop-flutter(app)', link: '/ecosystem/app'},
                            {text: 'linjiashop-uniapp', link: '/ecosystem/uniapp'},
                            {text: 'web-flash', link: 'http://webflash.enilu.cn/'},
                            {text: 'code-generator', link: 'http://webflash.enilu.cn/ecosystem/code-generator.html'},
                            {text: 'database-doc-generator', link: 'http://webflash.enilu.cn/ecosystem/database-doc-generator.html'},
                            {text: 'material-admin', link: 'https://enilu.gitee.io/material-admin'}
                        ]
                    },
                    {
                        text: '帮助', items: [
                            {text: '在线资源', link: '/guide/base/resource'},
                            {text: '问答社区', link: '/guide/other/xiaomiquan'},
                            {text: '视频教程', link: '/guide/other/video'},
                            {text: 'QQ群(254059156)', link: 'https://jq.qq.com/?_wv=1027&k=5rEmcb6'},
                            {text: '作者blog', link: 'http://blog.enilu.cn'},
                            {text: 'FAQ', link: '/guide/other/faq'},
                            {text: 'Change Log', link: '/guide/other/changelog'}
                        ]
                    },

                ]
            },
            {text: '上线实战', link: '/online/summary'},
            {text: '捐赠', link: '/donate'},
            {text: 'Gitee', link: 'https://gitee.com/microapp/linjiashop'}
        ],
        sidebar: {
            '/guide/': [
                {
                    title: '准备',
                    collapsable: false,
                    children: [
                        '/guide/base/preface',
                        '/guide/base/jdkAndMaven',
                        '/guide/base/modules'
                    ]
                },
                {
                    title: '快速开始',
                    collapsable: false,
                    children: [
                        '/guide/quickstart/quickstart',
                        '/guide/quickstart/clone',
                        '/guide/quickstart/initDb',
                        '/guide/quickstart/config',
                        '/guide/quickstart/startup'
                    ]
                },
                {
                    title: '二次开发',
                    collapsable: false,
                    children: [
                        '/guide/develop/develop',
                        '/guide/develop/api',
                        '/guide/develop/banner',
                        '/guide/develop/attribute',
                        '/guide/develop/wechat',
                        '/guide/develop/express'
                    ]
                }, {
                    title: '其他',
                    collapsable: false,
                    children: [
                        '/guide/other/faq',
                        '/guide/other/changelog'
                    ]
                }
            ], '/online/': [
                {
                    title: '上线实战',
                    collapsable: false,
                    children: [
                        '/online/summary',
                        '/online/day01',
                        '/online/day02',
                        '/online/day04',
                        '/online/day05',
                        '/online/day06'
                    ]
                }

            ], '/ecosystem/': [
                {
                    title: '生态系统',
                    collapsable: false,
                    children: [
                        '/ecosystem/app',
                        '/ecosystem/uniapp'
                    ]
                }]
        }

    }
}
