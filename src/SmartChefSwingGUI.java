import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class SmartChefSwingGUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SmartChefSwingGUI().showLoginWindow();
        });
    }

    private void showLoginWindow() {
        JFrame loginFrame = new JFrame("SmartChef - Login");
        loginFrame.setSize(350, 300);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("👨‍🍳 Welcome to SmartChef");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField usernameField = new JTextField(15);
        usernameField.setMaximumSize(usernameField.getPreferredSize());

        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setMaximumSize(passwordField.getPreferredSize());

        JButton loginButton = new JButton("Login");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton registerButton = new JButton("📝 Sign Up");
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel messageLabel = new JLabel(" ");
        messageLabel.setForeground(Color.RED);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // اکشن مربوط به دکمه لاگین
        loginButton.addActionListener((ActionEvent e) -> {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());

            // فراخوانی متد لاگین از کلاس UserDAO شما
            UserDAO dao = new UserDAO();
            User loggedInUser = dao.loginUser(user, pass);

            if (loggedInUser != null) {
                UserSession.login(loggedInUser);
                loginFrame.dispose();
                showDashboardWindow();
            } else {
                messageLabel.setText("❌ Invalid username or password!");
            }
        });

        // اکشن مربوط به دکمه ورود به صفحه ثبت نام
        registerButton.addActionListener(e -> {
            loginFrame.dispose();
            showRegisterWindow();
        });

        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(loginButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(registerButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(messageLabel);

        loginFrame.add(panel);
        loginFrame.setVisible(true);
    }

    private void showRegisterWindow() {
        JFrame registerFrame = new JFrame("SmartChef - Sign Up");
        registerFrame.setSize(350, 400);
        registerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        registerFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("📝 Create an Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField nameField = new JTextField(15);
        nameField.setMaximumSize(nameField.getPreferredSize());

        JTextField usernameField = new JTextField(15);
        usernameField.setMaximumSize(usernameField.getPreferredSize());

        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setMaximumSize(passwordField.getPreferredSize());

        JPasswordField confirmPasswordField = new JPasswordField(15);
        confirmPasswordField.setMaximumSize(confirmPasswordField.getPreferredSize());

        JButton btnSubmit = new JButton("Register");
        btnSubmit.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnBack = new JButton("🔙 Back to Login");
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel messageLabel = new JLabel(" ");
        messageLabel.setForeground(Color.RED);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnBack.addActionListener(e -> {
            registerFrame.dispose();
            showLoginWindow();
        });

        // اکشن مربوط به دکمه تایید ثبت نام
        btnSubmit.addActionListener(e -> {
            String name = nameField.getText();
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());
            String confirmPass = new String(confirmPasswordField.getPassword());

            // اعتبارسنجی اولیه فرم
            if (name.isEmpty() || user.isEmpty() || pass.isEmpty()) {
                messageLabel.setText("❌ All fields are required!");
            } else if (!pass.equals(confirmPass)) {
                messageLabel.setText("❌ Passwords do not match!");
            } else {
                // ساخت شیء User بر اساس استانداردی که خودت در UserDAO تعریف کردی
                User newUser = new User();
                newUser.setFirstName(name);
                newUser.setLastName(""); // فرم فعلی نام خانوادگی ندارد، پس خالی ارسال می‌کنیم
                newUser.setUsername(user);
                newUser.setPassword(pass);

                // استفاده از متد ثبت نام شما
                UserDAO dao = new UserDAO();
                boolean isRegistered = dao.registerUser(newUser);

                if (isRegistered) {
                    JOptionPane.showMessageDialog(registerFrame,
                        "✅ Account created successfully! Please login.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                    registerFrame.dispose();
                    showLoginWindow();
                } else {
                    messageLabel.setText("❌ Registration failed. Username might exist.");
                }
            }
        });

        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(new JLabel("First Name:"));
        panel.add(nameField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(new JLabel("Confirm Password:"));
        panel.add(confirmPasswordField);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(btnSubmit);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btnBack);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(messageLabel);

        registerFrame.add(panel);
        registerFrame.setVisible(true);
    }

    private void showDashboardWindow() {
        JFrame mainFrame = new JFrame("SmartChef - Main Menu");
        mainFrame.setSize(400, 350);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel welcomeLabel = new JLabel("Welcome, " + UserSession.getCurrentUser().getFirstName() + "! 🎉");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnRecipes = new JButton("🍲 Recipe Management");
        JButton btnMealPlan = new JButton("📅 Meal Planning");
        JButton btnShopping = new JButton("🛒 Shopping List");
        JButton btnNutrition = new JButton("📊 Nutrition & Health");
        JButton btnLogout = new JButton("🔙 Logout");

        JButton[] buttons = {btnRecipes, btnMealPlan, btnShopping, btnNutrition, btnLogout};
        for (JButton btn : buttons) {
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(250, 40));
        }

        btnRecipes.addActionListener(e -> showRecipeWindow());
        btnShopping.addActionListener(e -> showShoppingListWindow());
        btnMealPlan.addActionListener(e -> showMealPlanWindow());
        btnNutrition.addActionListener(e -> showNutritionWindow());

        btnLogout.addActionListener(e -> {
            UserSession.logout();
            mainFrame.dispose();
            showLoginWindow();
        });

        panel.add(welcomeLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        for (JButton btn : buttons) {
            panel.add(btn);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        mainFrame.add(panel);
        mainFrame.setVisible(true);
    }

    // =====================================================================
    // 🍲 RECIPE MANAGEMENT — now wired to RecipeDAO (same DB logic as the CLI)
    // =====================================================================

    private void showRecipeWindow() {
        JFrame recipeFrame = new JFrame("🍲 Recipe Management");
        recipeFrame.setSize(680, 450);
        recipeFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columns = {"ID", "Recipe Name", "Category", "Servings", "Prep Time (min)"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        JTable recipeTable = new JTable(tableModel);
        recipeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(recipeTable);

        int userId = UserSession.getCurrentUserId();
        RecipeDAO recipeDAO = new RecipeDAO();

        Runnable loadRecipes = () -> {
            tableModel.setRowCount(0);
            List<Recipe> recipes = recipeDAO.getRecipesByUserId(userId);
            if (recipes.isEmpty()) {
                JOptionPane.showMessageDialog(recipeFrame, "You don't have any recipes yet. Let's add some! 👨‍🍳");
            } else {
                for (Recipe r : recipes) {
                    tableModel.addRow(new Object[]{r.getId(), r.getRecipeName(), r.getCategory(), r.getServing(), r.getPreparationTime()});
                }
            }
        };

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField searchField = new JTextField(15);
        JButton btnSearch = new JButton("🔍 Search by Name");
        topPanel.add(new JLabel("Search:"));
        topPanel.add(searchField);
        topPanel.add(btnSearch);

        btnSearch.addActionListener(e -> {
            String keyword = searchField.getText().trim();
            if (keyword.isEmpty()) {
                loadRecipes.run();
                return;
            }
            tableModel.setRowCount(0);
            List<Recipe> results = recipeDAO.searchRecipesByName(keyword, userId);
            if (results.isEmpty()) {
                JOptionPane.showMessageDialog(recipeFrame, "No recipes found matching '" + keyword + "'. 😔");
            } else {
                for (Recipe r : results) {
                    tableModel.addRow(new Object[]{r.getId(), r.getRecipeName(), r.getCategory(), r.getServing(), r.getPreparationTime()});
                }
            }
        });

        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton btnLoad = new JButton("🔄 Load My Recipes");
        JButton btnAdd = new JButton("➕ Add New Recipe");
        JButton btnUpdate = new JButton("✏️ Update Selected");
        JButton btnDelete = new JButton("🗑️ Delete Selected");

        btnLoad.addActionListener(e -> loadRecipes.run());

        btnAdd.addActionListener(e -> showAddRecipeDialog(recipeFrame, userId, loadRecipes));

        btnUpdate.addActionListener(e -> {
            int row = recipeTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(recipeFrame, "⚠️ Please select a recipe first.");
                return;
            }
            int recipeId = (int) tableModel.getValueAt(row, 0);
            showUpdateRecipeDialog(recipeFrame, recipeId, loadRecipes);
        });

        btnDelete.addActionListener(e -> {
            int row = recipeTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(recipeFrame, "⚠️ Please select a recipe first.");
                return;
            }
            int recipeId = (int) tableModel.getValueAt(row, 0);
            String recipeName = (String) tableModel.getValueAt(row, 1);
            int confirm = JOptionPane.showConfirmDialog(recipeFrame,
                    "⚠️ Are you sure you want to delete '" + recipeName + "'?",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean deleted = recipeDAO.deleteRecipe(recipeId);
                if (deleted) {
                    JOptionPane.showMessageDialog(recipeFrame, "✅ Recipe and its ingredients deleted successfully!");
                    loadRecipes.run();
                } else {
                    JOptionPane.showMessageDialog(recipeFrame, "❌ Deletion failed!");
                }
            }
        });

        bottomPanel.add(btnLoad);
        bottomPanel.add(btnAdd);
        bottomPanel.add(btnUpdate);
        bottomPanel.add(btnDelete);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        recipeFrame.add(panel);
        recipeFrame.setVisible(true);

        loadRecipes.run(); // auto-load when the window opens
    }

    private void showAddRecipeDialog(JFrame parent, int userId, Runnable onSaved) {
        JDialog dialog = new JDialog(parent, "➕ Add New Recipe", true);
        dialog.setSize(460, 600);
        dialog.setLocationRelativeTo(parent);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JTextField nameField = new JTextField();
        nameField.setAlignmentX(Component.LEFT_ALIGNMENT);
        JTextField categoryField = new JTextField();
        categoryField.setAlignmentX(Component.LEFT_ALIGNMENT);
        JSpinner servingsSpinner = new JSpinner(new SpinnerNumberModel(4, 1, 100, 1));
        JSpinner prepTimeSpinner = new JSpinner(new SpinnerNumberModel(30, 1, 600, 5));
        JTextArea instructionsArea = new JTextArea(4, 20);
        instructionsArea.setLineWrap(true);
        instructionsArea.setWrapStyleWord(true);
        JScrollPane instructionsScroll = new JScrollPane(instructionsArea);
        instructionsScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        instructionsScroll.setMaximumSize(new Dimension(400, 90));

        formPanel.add(leftLabel("Recipe Name:"));
        formPanel.add(nameField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        formPanel.add(leftLabel("Category (e.g., Breakfast, Lunch, Dessert):"));
        formPanel.add(categoryField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        formPanel.add(leftLabel("Servings:"));
        formPanel.add(alignLeft(servingsSpinner));
        formPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        formPanel.add(leftLabel("Preparation Time (minutes):"));
        formPanel.add(alignLeft(prepTimeSpinner));
        formPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        formPanel.add(leftLabel("Instructions:"));
        formPanel.add(instructionsScroll);
        formPanel.add(Box.createRigidArea(new Dimension(0, 12)));

        // --- Ingredients section (mirrors the "add ingredients one by one" loop in the CLI) ---
        formPanel.add(leftLabel("Ingredients:"));
        DefaultListModel<String> ingredientListModel = new DefaultListModel<>();
        List<Ingredient> ingredients = new ArrayList<>();
        JList<String> ingredientJList = new JList<>(ingredientListModel);
        JScrollPane ingredientScroll = new JScrollPane(ingredientJList);
        ingredientScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        ingredientScroll.setPreferredSize(new Dimension(400, 80));
        ingredientScroll.setMaximumSize(new Dimension(400, 80));
        formPanel.add(ingredientScroll);

        JPanel ingredientInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ingredientInputPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JTextField ingNameField = new JTextField(8);
        JTextField ingAmountField = new JTextField(4);
        JTextField ingUnitField = new JTextField(6);
        JButton btnAddIngredient = new JButton("Add");
        ingredientInputPanel.add(new JLabel("Name:"));
        ingredientInputPanel.add(ingNameField);
        ingredientInputPanel.add(new JLabel("Amount:"));
        ingredientInputPanel.add(ingAmountField);
        ingredientInputPanel.add(new JLabel("Unit:"));
        ingredientInputPanel.add(ingUnitField);
        ingredientInputPanel.add(btnAddIngredient);
        formPanel.add(ingredientInputPanel);

        btnAddIngredient.addActionListener(e -> {
            String ingName = ingNameField.getText().trim();
            String amountText = ingAmountField.getText().trim();
            String unit = ingUnitField.getText().trim();
            if (ingName.isEmpty() || amountText.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "⚠️ Ingredient name and amount are required.");
                return;
            }
            try {
                double amount = Double.parseDouble(amountText);
                Ingredient ing = new Ingredient();
                ing.setIngredientName(ingName);
                ing.setAmount(amount);
                ing.setUnit(unit);
                ingredients.add(ing);
                ingredientListModel.addElement(ingName + " - " + amount + " " + unit);
                ingNameField.setText("");
                ingAmountField.setText("");
                ingUnitField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "⚠️ Amount must be a number.");
            }
        });

        JButton btnSave = new JButton("💾 Save Recipe");
        btnSave.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel messageLabel = new JLabel(" ");
        messageLabel.setForeground(Color.RED);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnSave.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                messageLabel.setText("❌ Recipe name is required!");
                return;
            }
            Recipe recipe = new Recipe();
            recipe.setUserId(userId);
            recipe.setRecipeName(name);
            recipe.setCategory(categoryField.getText().trim());
            recipe.setServing((Integer) servingsSpinner.getValue());
            recipe.setPreparationTime((Integer) prepTimeSpinner.getValue());
            recipe.setInstructions(instructionsArea.getText());

            RecipeDAO recipeDAO = new RecipeDAO();
            boolean success = recipeDAO.addRecipeWithIngredients(recipe, ingredients);
            if (success) {
                JOptionPane.showMessageDialog(dialog, "✅ Recipe '" + name + "' added successfully!");
                dialog.dispose();
                onSaved.run();
            } else {
                messageLabel.setText("❌ Failed to add the recipe. Please try again.");
            }
        });

        formPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        formPanel.add(btnSave);
        formPanel.add(messageLabel);

        dialog.add(new JScrollPane(formPanel));
        dialog.setVisible(true);
    }

    private void showUpdateRecipeDialog(JFrame parent, int recipeId, Runnable onSaved) {
        RecipeDAO recipeDAO = new RecipeDAO();
        Recipe existing = recipeDAO.getRecipeById(recipeId);
        if (existing == null) {
            JOptionPane.showMessageDialog(parent, "❌ Could not load recipe details.");
            return;
        }

        JDialog dialog = new JDialog(parent, "✏️ Update Recipe", true);
        dialog.setSize(420, 430);
        dialog.setLocationRelativeTo(parent);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JTextField nameField = new JTextField(existing.getRecipeName());
        JTextField categoryField = new JTextField(existing.getCategory());
        int currentServing = existing.getServing() > 0 ? existing.getServing() : 1;
        int currentPrepTime = existing.getPreparationTime() > 0 ? existing.getPreparationTime() : 1;
        JSpinner servingsSpinner = new JSpinner(new SpinnerNumberModel(currentServing, 1, 100, 1));
        JSpinner prepTimeSpinner = new JSpinner(new SpinnerNumberModel(currentPrepTime, 1, 600, 5));
        JTextArea instructionsArea = new JTextArea(existing.getInstructions(), 4, 20);
        instructionsArea.setLineWrap(true);
        instructionsArea.setWrapStyleWord(true);
        JScrollPane instructionsScroll = new JScrollPane(instructionsArea);

        formPanel.add(leftLabel("Recipe Name:"));
        formPanel.add(nameField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        formPanel.add(leftLabel("Category:"));
        formPanel.add(categoryField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        formPanel.add(leftLabel("Servings:"));
        formPanel.add(alignLeft(servingsSpinner));
        formPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        formPanel.add(leftLabel("Preparation Time (minutes):"));
        formPanel.add(alignLeft(prepTimeSpinner));
        formPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        formPanel.add(leftLabel("Instructions:"));
        formPanel.add(instructionsScroll);

        JButton btnSave = new JButton("💾 Save Changes");
        btnSave.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel messageLabel = new JLabel(" ");
        messageLabel.setForeground(Color.RED);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnSave.addActionListener(e -> {
            Recipe updated = new Recipe();
            updated.setId(recipeId);
            updated.setRecipeName(nameField.getText().trim());
            updated.setCategory(categoryField.getText().trim());
            updated.setServing((Integer) servingsSpinner.getValue());
            updated.setPreparationTime((Integer) prepTimeSpinner.getValue());
            updated.setInstructions(instructionsArea.getText());

            boolean success = recipeDAO.updateRecipe(updated);
            if (success) {
                JOptionPane.showMessageDialog(dialog, "✅ Recipe updated successfully!");
                dialog.dispose();
                onSaved.run();
            } else {
                messageLabel.setText("❌ Update failed! Are you sure the ID is correct?");
            }
        });

        formPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        formPanel.add(btnSave);
        formPanel.add(messageLabel);

        dialog.add(formPanel);
        dialog.setVisible(true);
    }

    // small helpers to keep BoxLayout form fields left-aligned
    private JLabel leftLabel(String text) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JPanel alignLeft(JComponent component) {
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        wrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        wrapper.add(component);
        return wrapper;
    }

    // =====================================================================
    // 🛒 SHOPPING LIST — already wired correctly, unchanged
    // =====================================================================

    private void showShoppingListWindow() {
        JFrame shopFrame = new JFrame("🛒 Shopping List");
        shopFrame.setSize(500, 400);
        shopFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> shoppingJList = new JList<>(listModel);
        shoppingJList.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(shoppingJList);

        JPanel topPanel = new JPanel(new FlowLayout());
        JButton btnGenerate = new JButton("🔄 Generate List");
        JButton btnView = new JButton("📋 View List");
        JButton btnPurchase = new JButton("✅ Mark as Purchased");

        topPanel.add(btnGenerate);
        topPanel.add(btnView);
        topPanel.add(btnPurchase);

        int userId = UserSession.getCurrentUserId();

        btnGenerate.addActionListener(e -> {
            try {
                ShoppingListService shopService = new ShoppingListService();
                ShoppingListDAO shopDAO = new ShoppingListDAO();
                List<Ingredient> generatedList = shopService.generateWeeklyShoppingList(userId);

                if (generatedList != null && !generatedList.isEmpty()) {
                    shopDAO.saveShoppingList(userId, generatedList);
                    JOptionPane.showMessageDialog(shopFrame, "✅ Shopping list generated successfully!");
                    btnView.doClick();
                } else {
                    JOptionPane.showMessageDialog(shopFrame, "Your meal plan is empty!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(shopFrame, "Error generating list: " + ex.getMessage());
            }
        });

        btnView.addActionListener(e -> {
            try {
                ShoppingListDAO shopDAO = new ShoppingListDAO();
                List<Ingredient> myList = shopDAO.getShoppingList(userId);
                listModel.clear();

                if (myList != null && !myList.isEmpty()) {
                    for (Ingredient item : myList) {
                        listModel.addElement("◽ " + item.getIngredientName() + " - " + item.getAmount() + " " + item.getUnit());
                    }
                } else {
                    listModel.addElement("Your shopping list is empty! 🛒");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(shopFrame, "Error viewing list: " + ex.getMessage());
            }
        });

        btnPurchase.addActionListener(e -> {
            String selected = shoppingJList.getSelectedValue();
            if (selected != null && selected.startsWith("◽ ")) {
                String itemName = selected.substring(2, selected.indexOf(" - "));
                ShoppingListDAO dao = new ShoppingListDAO();
                dao.updatePurchaseStatus(userId, itemName, true);
                JOptionPane.showMessageDialog(shopFrame, "✅ Item '" + itemName + "' marked as PURCHASED!");
                btnView.doClick();
            } else {
                JOptionPane.showMessageDialog(shopFrame, "⚠️ Please select an item from the list first.");
            }
        });

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        shopFrame.add(panel);
        shopFrame.setVisible(true);
    }

    // =====================================================================
    // 📅 MEAL PLANNING — now wired to MealPlanDAO (same DB logic as the CLI)
    // =====================================================================

    private void showMealPlanWindow() {
        JFrame mealFrame = new JFrame("📅 Meal Planning");
        mealFrame.setSize(650, 450);
        mealFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columns = {"Plan ID", "Day", "Meal Type", "Recipe"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        JTable mealTable = new JTable(tableModel);
        mealTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(mealTable);

        int userId = UserSession.getCurrentUserId();
        MealPlanDAO mealPlanDAO = new MealPlanDAO();

        Runnable loadPlan = () -> {
            tableModel.setRowCount(0);
            List<MealPlan> plans = mealPlanDAO.getWeeklyMealPlan(userId);
            if (plans.isEmpty()) {
                JOptionPane.showMessageDialog(mealFrame, "Your meal plan is empty! Let's add some meals. 🍽️");
            } else {
                for (MealPlan p : plans) {
                    tableModel.addRow(new Object[]{p.getId(), p.getDayOfWeek(), p.getMealType(), p.getRecipeName()});
                }
            }
        };

        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton btnLoadPlan = new JButton("🔄 Load Weekly Plan");
        JButton btnAssignMeal = new JButton("➕ Assign Recipe to Day");
        JButton btnAutoGenerate = new JButton("🤖 Auto-Generate Plan");
        JButton btnDeleteMeal = new JButton("🗑️ Remove Selected");

        btnLoadPlan.addActionListener(e -> loadPlan.run());

        btnAssignMeal.addActionListener(e -> showAssignMealDialog(mealFrame, userId, loadPlan));

        btnAutoGenerate.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(mealFrame,
                    "This will randomly assign one of your recipes to each day of the week for Lunch. Continue?",
                    "Auto-Generate Plan", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = mealPlanDAO.generateAutoWeeklyPlan(userId);
                if (success) {
                    JOptionPane.showMessageDialog(mealFrame, "✅ Weekly plan generated!");
                    loadPlan.run();
                } else {
                    JOptionPane.showMessageDialog(mealFrame, "❌ Could not generate a plan. Do you have any recipes yet?");
                }
            }
        });

        btnDeleteMeal.addActionListener(e -> {
            int row = mealTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(mealFrame, "⚠️ Please select a plan entry first.");
                return;
            }
            int planId = (int) tableModel.getValueAt(row, 0);
            boolean deleted = mealPlanDAO.deleteMealPlan(planId, userId);
            if (deleted) {
                JOptionPane.showMessageDialog(mealFrame, "✅ Removed from plan!");
                loadPlan.run();
            } else {
                JOptionPane.showMessageDialog(mealFrame, "❌ Could not remove entry.");
            }
        });

        bottomPanel.add(btnLoadPlan);
        bottomPanel.add(btnAssignMeal);
        bottomPanel.add(btnAutoGenerate);
        bottomPanel.add(btnDeleteMeal);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        mealFrame.add(panel);
        mealFrame.setVisible(true);

        loadPlan.run(); // auto-load when the window opens
    }

    private void showAssignMealDialog(JFrame parent, int userId, Runnable onSaved) {
        RecipeDAO recipeDAO = new RecipeDAO();
        List<Recipe> myRecipes = recipeDAO.getRecipesByUserId(userId);
        if (myRecipes.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "⚠️ You don't have any recipes yet. Add a recipe first!");
            return;
        }

        JDialog dialog = new JDialog(parent, "➕ Assign Recipe to Day", true);
        dialog.setSize(350, 300);
        dialog.setLocationRelativeTo(parent);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JComboBox<String> recipeCombo = new JComboBox<>();
        for (Recipe r : myRecipes) {
            recipeCombo.addItem(r.getId() + " - " + r.getRecipeName());
        }

        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        JComboBox<String> dayCombo = new JComboBox<>(days);

        String[] mealTypes = {"Breakfast", "Lunch", "Dinner", "Snack"};
        JComboBox<String> mealTypeCombo = new JComboBox<>(mealTypes);

        formPanel.add(leftLabel("Recipe:"));
        formPanel.add(recipeCombo);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(leftLabel("Day of Week:"));
        formPanel.add(dayCombo);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(leftLabel("Meal Type:"));
        formPanel.add(mealTypeCombo);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnSave = new JButton("💾 Assign");
        btnSave.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnSave.addActionListener(e -> {
            int selectedIndex = recipeCombo.getSelectedIndex();
            if (selectedIndex < 0) {
                JOptionPane.showMessageDialog(dialog, "⚠️ Please choose a recipe.");
                return;
            }
            int recipeId = myRecipes.get(selectedIndex).getId();

            MealPlan newPlan = new MealPlan();
            newPlan.setUserId(userId);
            newPlan.setRecipeId(recipeId);
            newPlan.setDayOfWeek((String) dayCombo.getSelectedItem());
            newPlan.setMealType((String) mealTypeCombo.getSelectedItem());

            MealPlanDAO mealPlanDAO = new MealPlanDAO();
            boolean success = mealPlanDAO.addMealPlan(newPlan);
            if (success) {
                JOptionPane.showMessageDialog(dialog, "✅ Meal assigned successfully!");
                dialog.dispose();
                onSaved.run();
            } else {
                JOptionPane.showMessageDialog(dialog, "❌ Failed to assign meal.");
            }
        });

        formPanel.add(btnSave);
        dialog.add(formPanel);
        dialog.setVisible(true);
    }

    // =====================================================================
    // 📊 NUTRITION & HEALTH — now wired to NutritionDAO (same DB logic as the CLI)
    // =====================================================================

    private void showNutritionWindow() {
        JFrame nutritionFrame = new JFrame("📊 Nutrition & Health");
        nutritionFrame.setSize(460, 520);
        nutritionFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        int userId = UserSession.getCurrentUserId();
        NutritionDAO nutritionDAO = new NutritionDAO();
        RecipeDAO recipeDAO = new RecipeDAO();
        List<Recipe> myRecipes = recipeDAO.getRecipesByUserId(userId);

        JLabel titleLabel = new JLabel("📊 Nutrition & Health");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- Add nutrition info section ---
        JPanel addPanel = new JPanel();
        addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));
        addPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        addPanel.setBorder(BorderFactory.createTitledBorder("➕ Add Nutrition Info to a Recipe"));

        JComboBox<String> recipeCombo = new JComboBox<>();
        for (Recipe r : myRecipes) {
            recipeCombo.addItem(r.getId() + " - " + r.getRecipeName());
        }

        JTextField caloriesField = new JTextField(6);
        JTextField proteinField = new JTextField(6);
        JTextField carbsField = new JTextField(6);
        JTextField fatField = new JTextField(6);

        JPanel macroPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        macroPanel.add(new JLabel("Calories:"));
        macroPanel.add(caloriesField);
        macroPanel.add(new JLabel("Protein (g):"));
        macroPanel.add(proteinField);
        macroPanel.add(new JLabel("Carbs (g):"));
        macroPanel.add(carbsField);
        macroPanel.add(new JLabel("Fat (g):"));
        macroPanel.add(fatField);

        JButton btnSaveNutrition = new JButton("💾 Save");
        JLabel addMessageLabel = new JLabel(" ");
        addMessageLabel.setForeground(Color.RED);

        btnSaveNutrition.addActionListener(e -> {
            int selectedIndex = recipeCombo.getSelectedIndex();
            if (selectedIndex < 0) {
                addMessageLabel.setText("⚠️ You need at least one recipe first.");
                return;
            }
            try {
                int recipeId = myRecipes.get(selectedIndex).getId();
                double calories = Double.parseDouble(caloriesField.getText().trim());
                double protein = Double.parseDouble(proteinField.getText().trim());
                double carbs = Double.parseDouble(carbsField.getText().trim());
                double fat = Double.parseDouble(fatField.getText().trim());

                Nutrition nutrition = new Nutrition(recipeId, calories, protein, carbs, fat);
                boolean success = nutritionDAO.saveOrUpdateNutrition(nutrition);
                if (success) {
                    addMessageLabel.setForeground(new Color(0, 128, 0));
                    addMessageLabel.setText("✅ Nutrition info saved!");
                } else {
                    addMessageLabel.setForeground(Color.RED);
                    addMessageLabel.setText("❌ Failed to save nutrition info.");
                }
            } catch (NumberFormatException ex) {
                addMessageLabel.setForeground(Color.RED);
                addMessageLabel.setText("⚠️ Please enter valid numbers.");
            }
        });

        addPanel.add(new JLabel("Recipe:"));
        addPanel.add(recipeCombo);
        addPanel.add(macroPanel);
        addPanel.add(btnSaveNutrition);
        addPanel.add(addMessageLabel);

        // --- Summary section ---
        JPanel sumPanel = new JPanel();
        sumPanel.setLayout(new BoxLayout(sumPanel, BoxLayout.Y_AXIS));
        sumPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sumPanel.setBorder(BorderFactory.createTitledBorder("📈 Nutrition Summary"));

        JLabel resultLabel = new JLabel("<html>Choose an option below to see your totals.</html>");
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        JComboBox<String> dayCombo = new JComboBox<>(days);

        JButton btnDaily = new JButton("📅 Daily Sum");
        JButton btnWeekly = new JButton("📈 Weekly Sum");

        btnDaily.addActionListener(e -> {
            String day = (String) dayCombo.getSelectedItem();
            Nutrition sum = nutritionDAO.getDailyNutritionSum(userId, day);
            resultLabel.setText("<html>" + day + ":<br>Calories: " + sum.getCalories() + " kcal<br>" +
                    "Protein: " + sum.getProtein() + " g &nbsp; Carbs: " + sum.getCarbs() +
                    " g &nbsp; Fat: " + sum.getFat() + " g</html>");
        });

        btnWeekly.addActionListener(e -> {
            Nutrition sum = nutritionDAO.getWeeklyNutritionSum(userId);
            resultLabel.setText("<html>Weekly Total:<br>Calories: " + sum.getCalories() + " kcal<br>" +
                    "Protein: " + sum.getProtein() + " g &nbsp; Carbs: " + sum.getCarbs() +
                    " g &nbsp; Fat: " + sum.getFat() + " g</html>");
        });

        JPanel sumButtonsPanel = new JPanel(new FlowLayout());
        sumButtonsPanel.add(dayCombo);
        sumButtonsPanel.add(btnDaily);
        sumButtonsPanel.add(btnWeekly);

        sumPanel.add(sumButtonsPanel);
        sumPanel.add(resultLabel);

        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(addPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(sumPanel);

        nutritionFrame.add(new JScrollPane(panel));
        nutritionFrame.setVisible(true);
    }
}
