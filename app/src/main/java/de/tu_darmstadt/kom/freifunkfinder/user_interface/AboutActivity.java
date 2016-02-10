/* AboutActivity - An activity to display About information.
 * Copyright (C) 2016  Sooraj Mandotti
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * sooraj.mandotti@stud.tu-darmstadt.de, Technical University Darmstadt
 *
 */

package de.tu_darmstadt.kom.freifunkfinder.user_interface;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    /**
     * Called by Android framework when this activity is created.
     *
     * @param savedInstanceState the bundle of saved instance stats.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity_main);
        TextView aboutTextView = (TextView)findViewById(R.id.about_text);
        StringBuilder aboutContent = new StringBuilder("Contributors : \n");
        aboutContent.append(" Govind Singh" + ", govind.singh@stud.tu-darmstadt.de, Technical University Darmstadt\n");
        aboutContent.append(" Puneet Arora" + ", puneet.arora@stud.tu-darmstadt.de, Technical University Darmstadt\n");
        aboutContent.append(" Sooraj Mandotti" + ", sooraj.mandotti@stud.tu-darmstadt.de, Technical University Darmstadt\n\n\n");
        aboutTextView.setText(aboutContent);
    }
}
