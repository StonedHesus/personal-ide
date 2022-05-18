package com.main;

import javax.swing.*;

import com.main.view.MainView;

public class Runner {
    /**
     * @param args
     *
     *
     * @since 0.0.1
     * @version final
     * @author Andrei-Paul Ionescu
     */

    public static void main(String[] args) {
	// write your code here
        SwingUtilities.invokeLater(MainView::new);
    }
}
