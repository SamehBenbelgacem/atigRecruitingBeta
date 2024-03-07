import { Authority } from 'app/config/authority.constants';
declare const adminRoot: string;

export interface NavbarItem {
  id?: string;
  icon?: string;
  label: string;
  to: string;
  newWindow?: boolean;
  subs?: NavbarItem[];
  roles?: Authority[];
}

const data: NavbarItem[] = [
  {
    icon: 'iconsminds-shop-4',
    label: 'menu.home',
    to: `${adminRoot}/home`,
  },
  {
    icon: 'iconsminds-shop-4',
    label: 'menu.entities',
    to: `${adminRoot}/entities`,

    subs: [
      {
        icon: 'simple-icon-briefcase',
        label: 'menu.Candidate',
        to: `${adminRoot}/menu/Candidate`,
      },
      {
        icon: 'simple-icon-briefcase',
        label: 'menu.Candidate Additional Infos',
        to: `${adminRoot}/menu/Candidate Additional Infos`,
      },
      {
        icon: 'simple-icon-briefcase',
        label: 'menu.Object Containing File',
        to: `${adminRoot}/menu/Object Containing File`,
      },
    ],
  },
];

export default NavbarItem;
