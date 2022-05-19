package com.main.view;

import com.main.configuration.Configuration;

import java.io.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import java.lang.*;

public class MainView extends JFrame implements Configuration {
    /**
     *
     * @since 0.0.1
     * @version 0.0.1
     * @author Andrei-Paul Ionescu
     */

    // Attributes of the class.

    // Constructors of the class.
    public MainView(){

        // Initialise the window.
        this.initialise();
        // Add the menu bar to the window in accordance with the operating system which the user is using.
        this.setJMenuBar(this.createMenuBar());

    }

    // Methods of the class.
    private JMenuBar createMenuBar(){
        /**
         *
         * @since 0.0.1
         * @version 0.0.1
         * @author Andrei-Paul Ionescu
         */

        String operatingSystem = System.getProperty("os.name");

        // Determine whether the user's software is a Macintosh or not, for if it is then the menubar will be displayed
        // using the default mac one.
        if(operatingSystem.startsWith("Mac")){

            System.setProperty("apple.laf.useScreenMenuBar", "true");
        }

        final JMenuBar menuBar = new JMenuBar();

        JMenu menuFile = new JMenu("File");
        menuFile.setMnemonic('f');

        JMenuItem item = new JMenuItem("New");
        item.setMnemonic('n');
        item.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_N, InputEvent.META_DOWN_MASK
        ));
        ActionListener listener = new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent event){
            }
        };
        item.addActionListener(listener);
        menuFile.add(item);

        menuBar.add(menuFile);

        return menuBar;
    }
    private void initialise(){
        /**
         *
         * @since 0.0.1
         * @version 0.0.1
         * @author Andrei-Paul Ionescu
         */

        // Set the title of the window and the size.
        setTitle(APP_NAME);
        setSize(INITIAL_WINDOW_SIZE);

        // Poised the window in the centre of the screen.
        setLocationRelativeTo(null);

        // Set the default close operation and make the frame visible.
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
