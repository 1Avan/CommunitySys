//对外暴露配置路由（常量路由）
export const constantRoute = [
  {
    path: '/login',
    component: () => import('@/views/login/index.vue'),
    name: 'login', //命名路由
    meta: {
      title: '登录', //表单标题
      hidden: true, //路由的标题是否再菜单中隐藏
    },
  },
  {
    path: '/index',
    component: () => import('@/views/index/index.vue'),
    name: 'index',
    meta: {
      title: '首页',
      hidden: true, //路由的标题是否再菜单中隐藏
    },
    redirect: '/index/home',
    children: [
      {
        path: '/index/home',
        component: () => import('@/views/index/content1/conOne.vue'),
      },
      {
        path: '/index/community',
        component: () =>
          import('@/views/index/content1/community/community.vue'),
        redirect: '/index/community/huoli',
        children: [
          {
            path: '/index/community/huoli',
            component: () =>
              import('@/views/index/content1/community/huoli.vue'),
          },
          {
            path: '/index/community/youxiu',
            component: () =>
              import('@/views/index/content1/community/youxiu.vue'),
          },
        ],
      },
      {
        path: '/index/newsletter',
        component: () => import('@/views/index/content1/newsletter.vue'),
      },
      {
        path: '/index/regulations',
        component: () => import('@/views/index/content1/regulations.vue'),
      },
      {
        path: '/index/activity',
        component: () => import('@/views/index/content1/activity.vue'),
      },
    ],
  },
  {
    //根路由
    path: '/',
    component: () => import('@/layout/index.vue'),
    name: 'layout',
    meta: {
      title: '首页', //表单标题
      icon: 'Compass',
    },
    redirect: '/index',
    children: [
      {
        path: '/user',
        component: () => import('@/views/user/index.vue'),
        meta: {
          title: '用户管理', //表单标题
          hidden: false, //路由的标题是否在菜单中隐藏
          icon: 'User', //菜单左侧的图标
          allowRoleId:[1,2,3]//允许role1，2，3访问
        },
      },
      {
        path: '/activity',
        component: () => import('@/views/activity/index.vue'),
        name: 'activity',
        meta: {
          title: '活动管理',
          hidden: false, //路由的标题是否再菜单中隐藏
          icon: 'Grid',
          allowRoleId:[1,2]
        },
      },
      {
        path: '/community',
        component: () => import('@/views/community/index.vue'),
        name: 'community',
        meta: {
          title: '社团管理',
          hidden: false, //路由的标题是否再菜单中隐藏
          icon: 'StarFilled',
          allowRoleId:[1]
        },
      },
      {
        path: '/newsletter',
        component: () => import('@/views/newsletter/index.vue'),
        name: 'newsletter',
        meta: {
          title: '社团快讯',
          hidden: false, //路由的标题是否再菜单中隐藏
          icon: 'Promotion',
          allowRoleId:[1]
        },
      },
    ],
  },

  {
    //404
    path: '/404',
    component: () => import('@/views/404/index.vue'),
    meta: {
      title: '出错了',
      hidden: true, //路由的标题是否再菜单中隐藏
    },
  },
  {
    path: '/:pathMatch(.*)',
    redirect: '404',
    name: 'Any',
    meta: {
      title: '任意组件',
      hidden: true, //路由的标题是否再菜单中隐藏
    },
  },
  {
    path: '/screen',
    component: () => import('@/views/screen/index.vue'),
    name: 'screen', //命名路由
    meta: {
      title: '数据大屏', //表单标题
      hidden: false, //路由的标题是否再菜单中隐藏
      icon: 'Monitor',
    },
  },
]
