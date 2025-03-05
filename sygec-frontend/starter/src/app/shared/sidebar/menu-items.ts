import { RouteInfo } from './sidebar.metadata';

export const ROUTES: RouteInfo[] = [
  {
    path: '',
    title: 'Personal',
    icon: '',
    class: 'nav-small-cap',
    label: '',
    labelClass: '',
    extralink: true,
    submenu: []
  },
  {
    path: '/starter',
    title: 'TABLEAU DE BORD',
    icon: 'mdi mdi-gauge',
    class: '',
    label: '',
    labelClass: '',
    extralink: false,
    submenu: []
  },
  {
    path: '/reference/reference',
    title: 'REFERENCES',
    icon: 'mdi mdi-note-plus',
    class: '',
    label: '',
    labelClass: '',
    extralink: false,
    submenu: []
  },
  {
    path: '/authentication',
    title: 'ADMINISTRATION',
    icon: 'mdi mdi-note-plus',
    class: '',
    label: '',
    labelClass: '',
    extralink: false,
    submenu: []
  },

  {
    path: '',
    title: 'GESTION DEMANDES',
    icon: 'mdi mdi-bullseye',
    class: 'has-arrow',
    label: '',
    labelClass: '',
    extralink: false,
    submenu: [
      {
        path: '/demande/demande',
        title: 'Faire une Demande',
        icon: '',
        class: '',
        label: '',
        labelClass: '',
        extralink: false,
        submenu: []
      },
      {
        path: '/demande/suivre-ma-demande',
        title: 'Suivre ma Demande',
        icon: '',
        class: '',
        label: '',
        labelClass: '',
        extralink: false,
        submenu: []
      },
      {
        path: '/demande/listes-des-demandes',
        title: 'Listes des Demandes',
        icon: '',
        class: '',
        label: '',
        labelClass: '',
        extralink: false,
        submenu: []
       }
      
    ]
  },

  {
    path: '',
    title: 'STATISTIQUES',
    icon: 'mdi mdi-arrange-send-backward',
    class: 'has-arrow',
    label: '',
    labelClass: '',
    extralink: false,
    submenu: [
      {
        path: ';',
        title: 'Etat entre Deux Dates',
        icon: '',
        class: '',
        label: '',
        labelClass: '',
        extralink: false,
        submenu: []
      },
      
     
    ]
  }
];
