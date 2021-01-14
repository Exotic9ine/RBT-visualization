
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class RBTreeTest {
    static RedBlackTree<Integer> tree;
    private static final int a[] = {10, 20, 40, 50, 70};
    private static final boolean mDebugInsert = false;
    private static final boolean mDebugDelete = false;

    public static void main(String[] args) {
        int i, ilen = a.length;
        tree=new RedBlackTree<Integer>();

        System.out.printf("== Raw Data: ");
        for(i=0; i<ilen; i++)
            System.out.printf("%d ", a[i]);
        System.out.printf("\n");

        for(i=0; i<ilen; i++) {
            tree.insert(a[i]);

            
        }

        System.out.printf("== Preorder traversal\n: ");
        tree.preOrder();


        System.out.printf("\n");


        tree.print();
        System.out.printf("\n");
        drawTree();


//        if (mDebugDelete) {
//            for(i=0; i<ilen; i++)
//            {
                //tree.delete(a[i]);
//
//                System.out.printf("== Delete node\n: %d\n", a[i]);
//                System.out.printf("== Tree details\n: \n");
//                tree.print();
//                System.out.printf("\n");
//            }
//        }



    }
    private static void drawTree() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {

                Frame frame = new Frame();

                frame.setVisible(true);

            }
        });
    }





    public static class Frame extends JFrame {
        public static final String TITLE = "Red Black Tree";
        public static final int HEIGHT = 500;
        public static final int WIDTH = (int) (HEIGHT/0.618);
        public Frame() {
            super();
            initFrame();
        }

        private void initFrame() {

            setTitle(TITLE);
            setSize(WIDTH, HEIGHT);

            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            setLocationRelativeTo(null);

            Panel panel = new Panel(this);
            setContentPane(panel);
        }

    }

    public static class Panel extends JPanel {
        private Frame frame;

        public Panel(Frame frame) {
            super();
            this.frame = frame;
        }

        private static void createUI(final JFrame frame) {
            JPanel panel = new JPanel();
            LayoutManager layout = new FlowLayout();
            panel.setLayout(layout);

            JButton button = new JButton("Click Me!");
            final JLabel label = new JLabel();
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String result = (String) JOptionPane.showInputDialog(
                            frame,
                            "Select one of the color",
                            "Swing Tester",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            null,
                            "Red"
                    );
                    if (result != null && result.length() > 0) {
                        label.setText("You selected:" + result);
                    } else {
                        label.setText("None selected");
                    }
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            ((Graphics2D)g).setColor(Color.RED);
            ((Graphics2D)g).drawLine(0, 25, 1366, 25);
            ((Graphics2D)g).drawLine(25, 768, 25, 0);
            //((Graphics2D)g).drawLine(50, 50, 200, 50);
            final Graphics2D g2 = (Graphics2D)g.create();
            final float ratio = frame.getWidth()*.8f/(tree.maximum() - tree.minimum());
            final float offset = tree.minimum()*ratio-25;

            g2.setFont(new Font(null, Font.ITALIC, 25));




            tree.SetInOrderDo(new RedBlackTree.inOrderDo(){

                public void dothis(Node n) {
                    Node<Integer> n2 = ((Node<Integer>)n);
                    if(n2.color==true)
                        g2.setColor(Color.black);
                    else g2.setColor(Color.red);
                    g2.drawString(n2.key+"",n2.key*ratio-offset ,tree.Counter2*100);
                    g2.setColor(Color.BLUE);
                    //draw lines
                    if(n2.parent!=null)
                        g2.drawLine((int)(n2.key*ratio-offset) ,tree.Counter2*100, (int)(n2.parent.key*ratio-offset), (tree.Counter2-1)*100);

                }});


            tree.inOrderDo();

            JButton button = new JButton("Click me");

        }



    }




}