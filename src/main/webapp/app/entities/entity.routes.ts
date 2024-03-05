import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'candidate',
    data: { pageTitle: 'Candidates' },
    loadChildren: () => import('./candidate/candidate.routes'),
  },
  {
    path: 'candidate-additional-infos',
    data: { pageTitle: 'CandidateAdditionalInfos' },
    loadChildren: () => import('./candidate-additional-infos/candidate-additional-infos.routes'),
  },
  {
    path: 'object-containing-file',
    data: { pageTitle: 'ObjectContainingFiles' },
    loadChildren: () => import('./object-containing-file/object-containing-file.routes'),
  },
  {
    path: 'experience',
    data: { pageTitle: 'Experiences' },
    loadChildren: () => import('./experience/experience.routes'),
  },
  {
    path: 'tool',
    data: { pageTitle: 'Tools' },
    loadChildren: () => import('./tool/tool.routes'),
  },
  {
    path: 'education',
    data: { pageTitle: 'Educations' },
    loadChildren: () => import('./education/education.routes'),
  },
  {
    path: 'certification',
    data: { pageTitle: 'Certifications' },
    loadChildren: () => import('./certification/certification.routes'),
  },
  {
    path: 'skill',
    data: { pageTitle: 'Skills' },
    loadChildren: () => import('./skill/skill.routes'),
  },
  {
    path: 'language',
    data: { pageTitle: 'Languages' },
    loadChildren: () => import('./language/language.routes'),
  },
  {
    path: 'category',
    data: { pageTitle: 'Categories' },
    loadChildren: () => import('./category/category.routes'),
  },
  {
    path: 'sub-category',
    data: { pageTitle: 'SubCategories' },
    loadChildren: () => import('./sub-category/sub-category.routes'),
  },
  {
    path: 'notification',
    data: { pageTitle: 'Notifications' },
    loadChildren: () => import('./notification/notification.routes'),
  },
  {
    path: 'note',
    data: { pageTitle: 'Notes' },
    loadChildren: () => import('./note/note.routes'),
  },
  {
    path: 'process',
    data: { pageTitle: 'Processes' },
    loadChildren: () => import('./process/process.routes'),
  },
  {
    path: 'process-step',
    data: { pageTitle: 'ProcessSteps' },
    loadChildren: () => import('./process-step/process-step.routes'),
  },
  {
    path: 'company',
    data: { pageTitle: 'Companies' },
    loadChildren: () => import('./company/company.routes'),
  },
  {
    path: 'desider',
    data: { pageTitle: 'Desiders' },
    loadChildren: () => import('./desider/desider.routes'),
  },
  {
    path: 'offer',
    data: { pageTitle: 'Offers' },
    loadChildren: () => import('./offer/offer.routes'),
  },
  {
    path: 'emailcredentials',
    data: { pageTitle: 'Emailcredentials' },
    loadChildren: () => import('./emailcredentials/emailcredentials.routes'),
  },
  {
    path: 'email',
    data: { pageTitle: 'Emails' },
    loadChildren: () => import('./email/email.routes'),
  },
  {
    path: 'sub-email',
    data: { pageTitle: 'SubEmails' },
    loadChildren: () => import('./sub-email/sub-email.routes'),
  },
  {
    path: 'event',
    data: { pageTitle: 'Events' },
    loadChildren: () => import('./event/event.routes'),
  },
  {
    path: 'tag',
    data: { pageTitle: 'Tags' },
    loadChildren: () => import('./tag/tag.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
