import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class FoodDeliveryApp extends JFrame {

    // =========================
    // DATA STRUCTURES
    // =========================

    static class Order {
        int id;
        String customer;
        String food;
        int quantity;
        String status;

        Order(int id, String customer, String food, int quantity) {
            this.id = id;
            this.customer = customer;
            this.food = food;
            this.quantity = quantity;
            this.status = "Pending";
        }
    }

    Queue<Order> orderQueue = new LinkedList<>();
    java.util.List<Order> deliveredOrders = new java.util.ArrayList<>();

    int nextOrderId = 101;

    // =========================
    // COLORS
    // =========================

    Color NAVY = new Color(28, 35, 51);
    Color ORANGE = new Color(242, 128, 48);
    Color LIGHT_ORANGE = new Color(255, 242, 232);
    Color BG = new Color(247, 248, 250);
    Color WHITE = Color.WHITE;
    Color GREY = new Color(105, 113, 125);
    Color GREEN = new Color(47, 158, 91);
    Color RED = new Color(210, 70, 70);

    JPanel contentPanel;
    JLabel pendingCount;
    JLabel preparingCount;
    JLabel deliveredCount;

    public FoodDeliveryApp() {
        setTitle("FoodExpress - Online Food Delivery Order Management");
        setSize(1200, 760);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        showCustomerScreen();
        setVisible(true);
    }

    // =========================
    // COMMON COMPONENTS
    // =========================

    JLabel label(String text, int size, Color color, boolean bold) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", bold ? Font.BOLD : Font.PLAIN, size));
        l.setForeground(color);
        return l;
    }

    JButton button(String text, Color background) {
        JButton b = new JButton(text);
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
        b.setForeground(WHITE);
        b.setBackground(background);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    JPanel card() {
        JPanel p = new JPanel();
        p.setBackground(WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 232, 236)),
                new EmptyBorder(15, 15, 15, 15)
        ));
        return p;
    }

    // =========================
    // CUSTOMER SCREEN
    // =========================

    void showCustomerScreen() {

        getContentPane().removeAll();

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(BG);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(NAVY);
        header.setBorder(new EmptyBorder(18, 28, 18, 28));

        JLabel logo = label("🍔 FoodExpress", 25, WHITE, true);

        JPanel headerButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        headerButtons.setOpaque(false);

        JButton restaurantButton = button("Restaurant Dashboard", ORANGE);
        headerButtons.add(restaurantButton);

        header.add(logo, BorderLayout.WEST);
        header.add(headerButtons, BorderLayout.EAST);

        main.add(header, BorderLayout.NORTH);

        // Content
        JPanel content = new JPanel(new BorderLayout(20, 20));
        content.setBackground(BG);
        content.setBorder(new EmptyBorder(25, 35, 25, 35));

        JPanel hero = new JPanel();
        hero.setLayout(new BoxLayout(hero, BoxLayout.Y_AXIS));
        hero.setBackground(NAVY);
        hero.setBorder(new EmptyBorder(25, 30, 25, 30));

        JLabel h1 = label("Order your favourite food", 30, WHITE, true);
        JLabel h2 = label("Fast • Fresh • Delivered to your door", 16,
                new Color(220, 224, 232), false);

        hero.add(h1);
        hero.add(Box.createVerticalStrut(8));
        hero.add(h2);

        content.add(hero, BorderLayout.NORTH);

        // Food cards
        JPanel foods = new JPanel(new GridLayout(1, 4, 18, 0));
        foods.setBackground(BG);

        addFoodCard(foods, "🍕", "Pizza", "₹199");
        addFoodCard(foods, "🍔", "Burger", "₹149");
        addFoodCard(foods, "🍛", "Biryani", "₹180");
        addFoodCard(foods, "🍜", "Noodles", "₹129");

        content.add(foods, BorderLayout.CENTER);

        JLabel bottom = label(
                "Every new order joins the queue and is processed in FIFO order.",
                14, GREY, false
        );
        bottom.setHorizontalAlignment(SwingConstants.CENTER);

        content.add(bottom, BorderLayout.SOUTH);

        main.add(content, BorderLayout.CENTER);

        restaurantButton.addActionListener(e -> showRestaurantScreen());

        add(main);
        revalidate();
        repaint();
    }

    void addFoodCard(JPanel parent, String icon, String food, String price) {

        JPanel p = card();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        JLabel iconLabel = label(icon, 48, NAVY, false);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel foodLabel = label(food, 20, NAVY, true);
        foodLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel priceLabel = label(price, 16, ORANGE, true);
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton order = button("ORDER NOW", ORANGE);
        order.setAlignmentX(Component.CENTER_ALIGNMENT);

        p.add(iconLabel);
        p.add(Box.createVerticalStrut(8));
        p.add(foodLabel);
        p.add(Box.createVerticalStrut(5));
        p.add(priceLabel);
        p.add(Box.createVerticalStrut(15));
        p.add(order);

        order.addActionListener(e -> showOrderDialog(food));

        parent.add(p);
    }

    // =========================
    // ORDER DIALOG
    // =========================

    void showOrderDialog(String food) {

        JTextField customerField = new JTextField();

        JSpinner quantitySpinner =
                new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 12));
        panel.setBorder(new EmptyBorder(10, 5, 5, 5));

        panel.add(label("Customer Name:", 14, NAVY, true));
        panel.add(customerField);

        panel.add(label("Quantity:", 14, NAVY, true));
        panel.add(quantitySpinner);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Place " + food + " Order",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {

            String customer = customerField.getText().trim();

            if (customer.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please enter your name.",
                        "Missing Information",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            int quantity = (int) quantitySpinner.getValue();

            Order order = new Order(
                    nextOrderId++,
                    customer,
                    food,
                    quantity
            );

            // ENQUEUE
            orderQueue.add(order);

            JOptionPane.showMessageDialog(
                    this,
                    "Order #" + order.id +
                            " placed successfully!\n\n" +
                            "Customer: " + order.customer +
                            "\nFood: " + order.food +
                            "\nQuantity: " + order.quantity +
                            "\n\nYour order has joined the queue.",
                    "Order Confirmed",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    // =========================
    // RESTAURANT DASHBOARD
    // =========================

    void showRestaurantScreen() {

        getContentPane().removeAll();

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(BG);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(NAVY);
        header.setBorder(new EmptyBorder(18, 28, 18, 28));

        JLabel title = label("🍔 FoodExpress  |  Restaurant Dashboard",
                24, WHITE, true);

        JPanel headerButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        headerButtons.setOpaque(false);

        JButton customerButton = button("Customer View", ORANGE);
        headerButtons.add(customerButton);

        header.add(title, BorderLayout.WEST);
        header.add(headerButtons, BorderLayout.EAST);

        main.add(header, BorderLayout.NORTH);

        // Main content
        JPanel content = new JPanel(new BorderLayout(18, 18));
        content.setBackground(BG);
        content.setBorder(new EmptyBorder(22, 30, 22, 30));

        // Stats
        JPanel stats = new JPanel(new GridLayout(1, 3, 15, 0));
        stats.setBackground(BG);

        JPanel pendingCard = createStatCard(
                "Pending Orders", "0", ORANGE
        );
        pendingCount = findValueLabel(pendingCard);

        JPanel preparingCard = createStatCard(
                "Preparing", "0", GREEN
        );
        preparingCount = findValueLabel(preparingCard);

        JPanel deliveredCard = createStatCard(
                "Delivered", "0", NAVY
        );
        deliveredCount = findValueLabel(deliveredCard);

        stats.add(pendingCard);
        stats.add(preparingCard);
        stats.add(deliveredCard);

        content.add(stats, BorderLayout.NORTH);

        // Queue area
        JPanel queueCard = card();
        queueCard.setLayout(new BorderLayout(10, 15));

        JPanel queueTitle = new JPanel(new BorderLayout());
        queueTitle.setOpaque(false);

        JLabel qt = label("ORDER QUEUE", 21, NAVY, true);
        JLabel info = label(
                "FIFO: First order placed → First order processed",
                13, GREY, false
        );

        queueTitle.add(qt, BorderLayout.WEST);
        queueTitle.add(info, BorderLayout.EAST);

        queueCard.add(queueTitle, BorderLayout.NORTH);

        JPanel queueVisual = new JPanel();
        queueVisual.setBackground(WHITE);
        queueVisual.setLayout(new BoxLayout(queueVisual, BoxLayout.X_AXIS));
        queueVisual.setBorder(new EmptyBorder(20, 15, 20, 15));

        updateQueueVisual(queueVisual);

        JScrollPane scroll = new JScrollPane(
                queueVisual,
                JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        scroll.setBorder(null);

        queueCard.add(scroll, BorderLayout.CENTER);

        // Buttons
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        actions.setOpaque(false);

        JButton processButton =
                button("⚡ PROCESS NEXT ORDER", ORANGE);

        JButton refreshButton =
                button("↻ Refresh Queue", NAVY);

        actions.add(processButton);
        actions.add(refreshButton);

        queueCard.add(actions, BorderLayout.SOUTH);

        content.add(queueCard, BorderLayout.CENTER);

        main.add(content, BorderLayout.CENTER);

        processButton.addActionListener(e -> processNextOrder(queueVisual));

        refreshButton.addActionListener(e -> {
            updateQueueVisual(queueVisual);
            updateStats();
        });

        customerButton.addActionListener(e -> showCustomerScreen());

        add(main);

        updateStats();

        revalidate();
        repaint();
    }

    JPanel createStatCard(String title, String value, Color accent) {

        JPanel p = card();
        p.setLayout(new BorderLayout());

        JLabel titleLabel = label(title, 14, GREY, false);
        JLabel valueLabel = label(value, 28, accent, true);

        p.add(titleLabel, BorderLayout.NORTH);
        p.add(valueLabel, BorderLayout.CENTER);

        return p;
    }

    JLabel findValueLabel(JPanel panel) {
        for (Component c : panel.getComponents()) {
            if (c instanceof JLabel) {
                JLabel l = (JLabel) c;
                if (l.getFont().getSize() == 28) {
                    return l;
                }
            }
        }
        return null;
    }

    // =========================
    // QUEUE VISUALIZATION
    // =========================

    void updateQueueVisual(JPanel queueVisual) {

        queueVisual.removeAll();

        if (orderQueue.isEmpty()) {

            JLabel empty = label(
                    "Queue is empty — place an order from Customer View.",
                    17, GREY, false
            );

            empty.setAlignmentX(Component.CENTER_ALIGNMENT);

            queueVisual.add(Box.createHorizontalGlue());
            queueVisual.add(empty);
            queueVisual.add(Box.createHorizontalGlue());

        } else {

            JLabel front = label("FRONT  →", 13, GREEN, true);
            queueVisual.add(front);
            queueVisual.add(Box.createHorizontalStrut(12));

            int index = 0;

            for (Order order : orderQueue) {

                JPanel orderCard = createOrderCard(order);

                queueVisual.add(orderCard);

                if (index < orderQueue.size() - 1) {
                    JLabel arrow = label("  →  ", 24, GREY, true);
                    queueVisual.add(arrow);
                }

                index++;
            }

            queueVisual.add(Box.createHorizontalStrut(12));

            JLabel rear = label("←  REAR", 13, ORANGE, true);
            queueVisual.add(rear);
        }

        queueVisual.revalidate();
        queueVisual.repaint();
    }

    JPanel createOrderCard(Order order) {

        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(205, 145));
        p.setMaximumSize(new Dimension(205, 145));
        p.setBackground(LIGHT_ORANGE);

        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ORANGE, 2),
                new EmptyBorder(12, 12, 12, 12)
        ));

        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        JLabel id = label("#" + order.id, 18, ORANGE, true);
        JLabel customer = label(order.customer, 15, NAVY, true);
        JLabel food = label(
                order.food + " × " + order.quantity,
                14, GREY, false
        );
        JLabel status = label("● " + order.status, 13, GREEN, true);

        p.add(id);
        p.add(Box.createVerticalStrut(7));
        p.add(customer);
        p.add(Box.createVerticalStrut(5));
        p.add(food);
        p.add(Box.createVerticalStrut(9));
        p.add(status);

        return p;
    }

    // =========================
    // PROCESS / DEQUEUE
    // =========================

    void processNextOrder(JPanel queueVisual) {

        if (orderQueue.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "There are no pending orders.",
                    "Queue Empty",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        // DEQUEUE
        Order order = orderQueue.poll();

        order.status = "Delivered";
        deliveredOrders.add(order);

        updateQueueVisual(queueVisual);
        updateStats();

        JOptionPane.showMessageDialog(
                this,
                "Order #" + order.id + " processed!\n\n" +
                        "Customer: " + order.customer + "\n" +
                        "Food: " + order.food + "\n" +
                        "Quantity: " + order.quantity +
                        "\n\n✓ Order Delivered",
                "Order Processed",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =========================
    // STATS
    // =========================

    void updateStats() {

        if (pendingCount != null) {
            pendingCount.setText(String.valueOf(orderQueue.size()));
        }

        if (preparingCount != null) {
            preparingCount.setText("0");
        }

        if (deliveredCount != null) {
            deliveredCount.setText(
                    String.valueOf(deliveredOrders.size())
            );
        }
    }

    // =========================
    // MAIN
    // =========================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new FoodDeliveryApp();
        });
    }
}
