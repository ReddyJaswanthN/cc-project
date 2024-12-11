package nine_mens_morris_game.main_Entry;

import java.awt.EventQueue;

import nine_mens_morris_game.view.MainUIWindow;

public class NineMensMorrisRunner {

    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
           @Override
            public void run()
            {
                new MainUIWindow();
            }
        });
    }
}