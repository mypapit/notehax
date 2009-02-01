/*
 * SplashCanvas.java
 *
 * Copyright (C) 2005 Tommi Laukkanen
 * http://www.substanceofcode.com
 *
 * Created on May 16th 2005, 21:51
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

//package substanceofcode;

import javax.microedition.lcdui.*;


/**
 *
 * @author Tommi Laukkanen
 */
public class SplashCanvas extends Canvas{

    private Image m_splashImage;
    private NoteHax m_midlet = null;

    /** Creates a new instance of SplashCanvas */
    public SplashCanvas(NoteHax midlet) {
        m_splashImage = loadImage("/s.png");//splashImage;
        m_midlet = midlet;

        /** Activate Full Screen Mode */
        this.setFullScreenMode( true );
    }

    /** Load an image */
    private Image loadImage(String filename) {
        //System.out.println("Loading image: " + filename);
        Image image = null;
        try {
            image = Image.createImage(filename);
        } catch(Exception e) {
        //    System.out.println("Error while loading image: " + filename);
        //    System.out.println("Description: " + e.toString());
            // Use null
        }
        return image;
    }

    /** Paint the screen */
    public void paint(Graphics g) {
        /** Paint the background white */
        g.setColor( 255, 255, 255 );
        g.fillRect( 0, 0, this.getWidth(), this.getHeight());

        /** Draw the icon */
        if(m_splashImage!=null) {
            g.drawImage( m_splashImage,
                    this.getWidth()/2,
                    this.getHeight()/2,
                    g.VCENTER|g.HCENTER);
        }
    }

    /** Move along after the keypress */
    protected void keyPressed(int keyCode) {
        m_midlet.showList();
    }
}
