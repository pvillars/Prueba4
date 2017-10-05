package cl.anpetrus.prueba4.adapters;

/**
 * Created by USUARIO on 02-10-2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cl.anpetrus.prueba4.views.main.fragments.CharactersFragment;
import cl.anpetrus.prueba4.views.main.fragments.ComicsFragment;
import cl.anpetrus.prueba4.views.main.fragments.EventsFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapters extends FragmentPagerAdapter {

        public SectionsPagerAdapters(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return CharactersFragment.newInstance();
                case 1:
                    return ComicsFragment.newInstance();
                case 2:
                    return EventsFragment.newInstance();
            }
            return CharactersFragment.newInstance();
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Personajes";
                case 1:
                    return "Comics";
                case 2:
                    return "Eventos";
            }
            return null;
        }
}
