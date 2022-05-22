package com.main.view;

import com.main.configuration.Configuration;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import java.lang.*;
import java.util.List;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.tree.*;

public class MainView extends JFrame implements Configuration {
    /**
     *
     * @since 0.0.1
     * @version 0.0.1
     * @author Andrei-Paul Ionescu
     */

    // Attributes of the class.
    protected JTextArea mainView_editor; // The editor's text area.

    protected JTree mainView_hierarchicalTree = null; // The hierarchical tree of the current project.

    protected JFileChooser mainView_chooser; // The file chooser object which enables us to navigate through the system.

    protected boolean mainView_textChanged = false; // A boolean which tells us whether the current file needs to be changed or not.

    protected File mainView_currentFile = null; // A reference to the current file which helps us display it.

    // Constructors of the class.
    public MainView(){

        // Initialise the window.
        this.initialise();

        // Add the text editor portion of the IDE.
        this.createTextEditorArea();

        // Add the menu bar to the window in accordance with the operating system which the user is using.
        this.setJMenuBar(this.createMenuBar());

        // Instantiate the JFileChooser object.
        this.mainView_chooser = new JFileChooser();
        this.mainView_chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        // Update the editor in accordance to any events which were triggered by the user.
        this.update();
    }

    // Methods of the class.
    private void highLightCurrentLine() throws BadLocationException {
        /**
         * @since 0.0.1
         * @version 0.0.1
         * @author Andrei-Paul Ionescu.
         */

        if(this.mainView_currentFile == null) return;

        final int position = this.mainView_editor.getText().indexOf(0);
        this.mainView_editor.getHighlighter().addHighlight(position,
                position, new DefaultHighlighter.DefaultHighlightPainter(Color.CYAN));

    }

    private void update(){
        /**
         * @since 0.0.1
         * @version 0.0.1
         * @author Andrei-Paul Ionescu.
         */

        try {
            this.highLightCurrentLine();
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }


    private void open(){
        /**
         * Determine whether the object which the user is trying to open is a file or a directory and call the
         * corresponding method for whichever the case might be.
         *
         * @since 0.0.1
         * @version 0.0.1
         * @author Andrei-Paul Ionescu.
         */

        if(this.mainView_chooser.showOpenDialog(MainView.this) != JFileChooser.APPROVE_OPTION) return;

        File flot = this.mainView_chooser.getSelectedFile();
        if(flot == null)
            return ;

        if(Files.isDirectory(flot.getParentFile().toPath())) openDirectory(flot);
        else openFile(flot);

    }


    private void openDirectory(File flot){

        // Add the hierarchical data of the selected project/folder to the IDE.

        this.createHierarchicalTree(flot);
    }


    private void openFile(File flot){

        this.mainView_currentFile = flot;
        try{

            FileReader in = new FileReader(flot);
            this.mainView_editor.read(in, null);
            in.close();
            setTitle(APP_NAME + " - " + flot.getName());
        } catch (IOException error) {
            error.printStackTrace();
        }

        this.mainView_textChanged = false;
        mainView_editor.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {mainView_textChanged = true;}

            @Override
            public void removeUpdate(DocumentEvent e) {mainView_textChanged = true;}

            @Override
            public void changedUpdate(DocumentEvent e) {mainView_textChanged = true;}
        });
    }


    private void createTextEditorArea(){
        /**
         *
         * @since 0.0.1
         * @version 0.0.1
         * @author Andrei-Paul Ionescu.
         */

        this.mainView_editor = new JTextArea();
        JScrollPane slider = new JScrollPane(this.mainView_editor);
        this.getContentPane().add(slider, BorderLayout.CENTER);
    }


    private void createHierarchicalTree(File flot){
        /**
         *
         * @since 0.0.1
         * @version 0.0.1
         * @author Andrei-Paul Ionescu.
         */

        if(this.mainView_hierarchicalTree != null)
            this.mainView_hierarchicalTree = null;

        // Create the root note of the currently selected folder.
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(flot.getName());

        // Create the child nodes of the currently selected folder.
        List<DefaultMutableTreeNode> nodes = new LinkedList<>();



        // Add the children to the root.
        for(DefaultMutableTreeNode node : nodes){

            root.add(node);
        }

        // Create a new JTree object and connect it with our root then add the scroller and then collate everything
        // to the current content pane.
        this.mainView_hierarchicalTree = new JTree(root);
        JScrollPane slider = new JScrollPane(this.mainView_hierarchicalTree);
        this.getContentPane().add(slider, BorderLayout.WEST);
    }


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

                // If the option to open is triggered then respond to the event via the aid of the open method.
                open();
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
