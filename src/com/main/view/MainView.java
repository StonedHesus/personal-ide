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

        // Create a new dropdown menu for the options which have to do with the files which are manipulated by the
        // IDE.
        JMenu menuFile = new JMenu("File");
        menuFile.setMnemonic('f');

        // Add a new option which for now will only create a new file of a specific extension.
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

        // Add a new option which allows the user to open a specific file by specifying a path towards where that file
        // is located.
        item = new JMenuItem("Open...");
        item.setMnemonic('o');
        item.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_O, InputEvent.META_DOWN_MASK
        ));
        listener = new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent event){

            }
        };
        item.addActionListener(listener);
        menuFile.add(item);

        // Here we add the save file option which saves a file utilising its current extension.
        item = new JMenuItem("Save");
        item.setMnemonic('s');
        item.setAccelerator(KeyStroke.getKeyStroke(
           KeyEvent.VK_S, InputEvent.META_DOWN_MASK
        ));
        listener = new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent event){

            }
        };
        item.addActionListener(listener);
        menuFile.add(item);

        // Here we collate to the File options menu the ability to save a file with a specific extension.
        item = new JMenuItem("Save as...");
        item.setMnemonic('a');
        item.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_S, InputEvent.META_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK
        ));
        listener = new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent event){

            }
        };
        item.addActionListener(listener);
        menuFile.add(item);

        // Add the File dropdown menu to the menubar.
        menuBar.add(menuFile);

        // Create a new dropdown menu for Edit functionalities of the IDE.
        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic('e');

        // Allow the user to undo something which it has typed.
        item = new JMenuItem("Undo Typing");
        item.setMnemonic('u');
        item.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_Z, InputEvent.META_DOWN_MASK
        ));
        listener = new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent event){}
        };
        item.addActionListener(listener);
        editMenu.add(item);

        // Give a user the option to redo the previous action undone.
        item = new JMenuItem("Redo");
        item.setMnemonic('r');
        item.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_Z, InputEvent.META_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK
        ));
        listener = new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent event){}
        };
        item.addActionListener(listener);
        editMenu.add(item);

        // Add the Edit dropdown menu to the menubar.
        menuBar.add(editMenu);

        return menuBar;
    }
    private void initialise() {
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
