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
      path: '/importData',
      name: 'importData',
      meta: {
        title: 'Import Data Page'
      },
      component: () => import(/* webpackChunkName: "importData" */ './views/ImportData.vue')
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
      path: '/ImportDataNew',
      name: 'ImportDataNew',
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
      path: '/PresentationData',
      name: 'PresentationData',
      meta: {
        title: 'Presentation Data Page'
      },
      component: () => import(/*webpackChunkName: "fileData" */ './views/PresentationData.vue')
    }


  ]
})
