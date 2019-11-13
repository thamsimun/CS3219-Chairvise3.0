import Vue from 'vue'
import Router from 'vue-router'
import {ID_NEW_PRESENTATION} from "@/common/const";

Vue.use(Router);

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/home',
      name: 'home',
      meta: {
        title: 'Home Page'
      },
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import(/* webpackChunkName: "home" */ './views/Home.vue')
    },
    {
      path: '/analyze',
      redirect: '/analyze/' + ID_NEW_PRESENTATION,
      meta: {
        title: 'New Analyze Page'
      },
    },
    {
      path: '/analyze/:id',
      name: 'analyze',
      meta: {
        title: 'Analyze Detail Page'
      },
      component: () => import(/* webpackChunkName: "analyze" */ './views/Analyze.vue'),
      props: true
    },
    {
      path: '/landing',
      name: 'landing',
      meta: {
        title: 'Landing Page'
      },
      component: () => import(/* webpackChunkName: "landing" */ './views/Landing.vue')
    },
    {
      path: '/guide',
      name: 'guide',
      meta: {
        title: 'Guide Page'
      },
      component: () => import(/* webpackChunkName: "guide" */ './views/Guide.vue')
    },
    {
      path: '/explore',
      name: 'explore',
      meta: {
        title: 'Explore Page'
      },
      component: () => import(/* webpackChunkName: "landing" */ './views/Explore.vue')
    },
    {
      path: '/ImportData',
      name: 'ImportData',
      meta: {
        title: 'Import Data Page'
      },
      component: () => import(/* webpackChunkName: "importDataNew" */ './views/ImportDataNew.vue')
    },
    {
      path: '/',
      redirect: '/landing'
    },
    {
      path: '/FileData',
      name: 'FileData',
      meta: {
        title: 'File Data Page'
      },
      component: () => import(/*webpackChunkName: "fileData" */ './views/FileData.vue')
    },
    {
      path: '/PresentationCreated',
      name: 'PresentationCreated',
      meta: {
        title: 'Presentation Created Page'
      },
      component: () => import(/*webpackChunkName: "presentationData" */ './views/PresentationCreated.vue')
    },
    {
      path: '/PresentationShared',
      name: 'PresentationShared',
      meta: {
        title: 'Presentation Shared Page'
      },
      component: () => import(/*webpackChunkName: "presentationShared" */ './views/PresentationShared.vue')
    },
    {
      path: '/userHome',
      name: 'userHome',
      meta: {
        title: 'User Home'
      },
      component: () => import(/*webpackChunkName: "userHome" */ './views/UserHome.vue')
    }
  ]
})
