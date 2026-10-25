//250055392

package lostandfoundsystem.windows;

import lostandfoundsystem.components.PageHeaderPanel;
import lostandfoundsystem.components.SideBarPanel;
import lostandfoundsystem.components.UIComponents;
import lostandfoundsystem.constants.Colors;
import lostandfoundsystem.constants.Fonts;

import lostandfoundsystem.domain.User;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import lostandfoundsystem.dao.LostItemDAO;
import lostandfoundsystem.dao.ReportDao;
import lostandfoundsystem.domain.Item;
import lostandfoundsystem.domain.Report;


public class ReportFoundItemWindow extends JFrame implements ActionListener, MouseListener {
    
    private User currentUser;

    private javax.swing.JButton cancelBtn;
    private javax.swing.JButton subBtn;
    private javax.swing.JButton uploadBtn;

    private JLabel itemTypeheading;
    private JLabel itemlbl;
    private JLabel categorylbl;
    private JLabel datelbl;
    private JLabel locationLbl;
    private JLabel descriptionlbl;
    private JLabel itemImagelbl;
    private JLabel imageSpaceLbl;

    private JTextField nameField;
    private JTextField dateField;
    private JTextField locationField;

    private JComboBox<String> categories;
    private JTextArea descriptionArea;

    private javax.swing.ImageIcon originalImage;
    private JFileChooser fileChooser;
    private String imagePath = "";

    Item item = new Item();
    LostItemDAO itemDao = new LostItemDAO();
    ReportDao reportDao = new ReportDao();
    Report report = new Report();
    
    public ReportFoundItemWindow(User currentUser) {
        this.currentUser = currentUser;
        super("Campus Finder - Report Found Item");
        guiSetUp();
    }

    private void guiSetUp() { 

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(Colors.DARK_BLUE_TEXT_COLOR);
        setLayout(new BorderLayout(15, 15));

        SideBarPanel sidebarPanel = new SideBarPanel(currentUser);

        JPanel center = new JPanel(new BorderLayout(15, 15));
        center.setOpaque(false);

        PageHeaderPanel headerPanel = new PageHeaderPanel("REPORT FOUND ITEM",currentUser);

        center.add(headerPanel, BorderLayout.NORTH);
        center.add(createContentPanel(), BorderLayout.CENTER);

        add(sidebarPanel, BorderLayout.WEST);
        add(center, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createContentPanel() {

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Colors.MOCKUP_FORM_CARD_BG);
        contentPanel.setBorder(new EmptyBorder(25, 30, 25, 30));

        JPanel formContainer = new JPanel(new BorderLayout(0, 15));
        formContainer.setOpaque(false);

        itemTypeheading = new JLabel("Found Item");
        itemTypeheading.setFont(Fonts.Bold.deriveFont(20f));
        itemTypeheading.setForeground(Colors.DARK_BLUE_TEXT_COLOR);
        itemTypeheading.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titlePanel.setOpaque(false);
        titlePanel.add(itemTypeheading);

        formContainer.add(titlePanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(1, 2, 30, 0));
        formPanel.setOpaque(false);

        formPanel.add(createDetailsPanel());
        formPanel.add(createImagePanel());

        formContainer.add(formPanel, BorderLayout.CENTER);

        formContainer.add(createButtonPanel(), BorderLayout.SOUTH);

        contentPanel.add(formContainer, BorderLayout.CENTER);

        return contentPanel;
    }

    private JPanel createDetailsPanel() {

        UIComponents.RoundedPanel detailsPanel =new UIComponents.RoundedPanel(20,Colors.LOGIN_BACKGROUND_COLOR);

        detailsPanel.setLayout(new BorderLayout());
        detailsPanel.setBorder(new EmptyBorder(25, 30, 25, 30));

        JPanel fieldsPanel = new JPanel(new GridLayout(4, 2, 15, 20));

        fieldsPanel.setOpaque(false);

        itemlbl = new JLabel("Item Name:");
        categorylbl = new JLabel("Category:");
        datelbl = new JLabel("Date Lost:");
        locationLbl = new JLabel("Location:");

        styleLabel(itemlbl);
        styleLabel(categorylbl);
        styleLabel(datelbl);
        styleLabel(locationLbl);

        nameField = new JTextField();
        dateField = new JTextField();
        locationField = new JTextField();

        categories = new JComboBox<>(
                new String[]{
                        "Select category",
                        "Electronics",
                        "Books & Stationary",
                        "Cards and Document",
                        "Sport Equipment",
                        "Clothing",
                        "Keys",
                        "Bottles & Boxes",
                        "Jewellery",
                        "Other"
                }
        );

        fieldsPanel.add(itemlbl);
        fieldsPanel.add(nameField);

        fieldsPanel.add(categorylbl);
        fieldsPanel.add(categories);

        fieldsPanel.add(datelbl);
        fieldsPanel.add(dateField);

        fieldsPanel.add(locationLbl);
        fieldsPanel.add(locationField);

        descriptionPanel();

        JPanel mainFieldsPanel = new JPanel(new BorderLayout(0, 20));
        mainFieldsPanel.setOpaque(false);
        mainFieldsPanel.add(fieldsPanel,BorderLayout.NORTH);
        mainFieldsPanel.add(createDescriptionPanel(),BorderLayout.CENTER);

        detailsPanel.add(mainFieldsPanel,BorderLayout.CENTER);

        return detailsPanel;
    }

    private void descriptionPanel() {
        descriptionArea = new JTextArea(8, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setFont(Fonts.Regular.deriveFont(13f));
    }

    private JPanel createDescriptionPanel() {

        JPanel descriptionPanel = new JPanel(new BorderLayout(0, 8));
        descriptionPanel.setOpaque(false);

        descriptionlbl = new JLabel("Description:");
        styleLabel(descriptionlbl);

        JScrollPane scrollPane = new JScrollPane(descriptionArea);

        descriptionPanel.add( descriptionlbl,BorderLayout.NORTH);
        descriptionPanel.add(scrollPane,BorderLayout.CENTER);

        return descriptionPanel;
    }

    private JPanel createImagePanel() {

        UIComponents.RoundedPanel imagePanel = new UIComponents.RoundedPanel(20,Colors.LOGIN_BACKGROUND_COLOR);

        imagePanel.setLayout(new BorderLayout(0, 15));

        imagePanel.setBorder(new EmptyBorder(25, 25, 25, 25));

        itemImagelbl = new JLabel("Upload Image:");

        styleLabel(itemImagelbl);

        imagePanel.add( itemImagelbl, BorderLayout.NORTH);

        imageSpaceLbl = new JLabel("No image selected", SwingConstants.CENTER);
        imageSpaceLbl.setOpaque(true);
        imageSpaceLbl.setBackground(Color.WHITE);
        imageSpaceLbl.setBorder(BorderFactory.createLineBorder(Colors.BORDER_GRAY));

        imagePanel.add(imageSpaceLbl,BorderLayout.CENTER);

        uploadBtn = new UIComponents.RoundedButton("Upload Image",Colors.ACCENT_BLUE_BUTTON, Colors.WHITE_TEXT_COLOR,15);
        uploadBtn.setPreferredSize(new Dimension(160, 40));
        uploadBtn.addActionListener(this);

        JPanel uploadButtonPanel =new JPanel(new FlowLayout(FlowLayout.CENTER));
        uploadButtonPanel.setOpaque(false);
        uploadButtonPanel.add(uploadBtn);

        imagePanel.add(uploadButtonPanel,BorderLayout.SOUTH);

        imageSpaceLbl.addMouseListener(this);

        return imagePanel;
    }

    private JPanel createButtonPanel() {

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,10));

        buttonsPanel.setOpaque(false);

        cancelBtn = new UIComponents.RoundedButton("Cancel",Colors.BLACK_BUTTON_COLOR,Colors.WHITE_TEXT_COLOR,15);
        subBtn = new UIComponents.RoundedButton("Submit",Colors.ACCENT_BLUE_BUTTON,Colors.WHITE_TEXT_COLOR,15);
        cancelBtn.setPreferredSize(new Dimension(130, 40));
        subBtn.setPreferredSize(new Dimension(130, 40));
        cancelBtn.addActionListener(this);
        subBtn.addActionListener(this);

        buttonsPanel.add(cancelBtn);
        buttonsPanel.add(subBtn);

        return buttonsPanel;
    }

    private void styleLabel(JLabel label) {

        label.setFont(Fonts.Bold.deriveFont(14f));
        label.setForeground(Colors.DARK_BLUE_TEXT_COLOR);
    }

    private void clearForm() {

        nameField.setText("");
        dateField.setText("");
        locationField.setText("");
        descriptionArea.setText("");

        categories.setSelectedIndex(0);

        imageSpaceLbl.setIcon(null);
        imageSpaceLbl.setText("No image selected");

        originalImage = null;
    }
    
    public void submitReport(){
       
        String itemName = nameField.getText();
        String date = dateField.getText();
        String location = locationField.getText();
        String description = descriptionArea.getText();
        String category = categories.getSelectedItem().toString();
        
        
        try{
            Integer.parseInt(itemName);
            return;
        }
        
        catch(NumberFormatException e){
            
        }
        
         try{
            Integer.parseInt(date);
            return;
        }
        
        catch(NumberFormatException e){
            
        }
         
          try{
            Integer.parseInt(location);
            return;
        }
        
        catch(NumberFormatException e){
            
        }
          
           try{
            Integer.parseInt(description);
            return;
        }
        
        catch(NumberFormatException e){
            
        }
           
           if(itemName.equals("")){
               JOptionPane.showMessageDialog(this, "Please enter the item name",null, JOptionPane.WARNING_MESSAGE);
               return;
           }
           
           if(category.equals("Select category")){
               JOptionPane.showMessageDialog(this, "Please select category",null, JOptionPane.WARNING_MESSAGE);
               return;
           }
           
           if(date.equals("")){
               JOptionPane.showMessageDialog(this, "Please enter the date you lost you item",null, JOptionPane.WARNING_MESSAGE);
               return;
           }
           
           if(description.equals("")){
               JOptionPane.showMessageDialog(this, "Please describe your item",null, JOptionPane.WARNING_MESSAGE);
               return;
           }
           
           if(location.equals("")){
               JOptionPane.showMessageDialog(this, "Please specify the location",null, JOptionPane.WARNING_MESSAGE);
               return;
           }
           
           if(originalImage==null){
               return;
           }
        
        item.setItemName(itemName);
        item.setCategory(category);
        item.setDescription(description);
        item.setStatus("Pending");
        report.setLocation(location);
        report.setItemType("Found");
        report.setDateLost(date);
        
        try {
              byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
             report.setImageData(imageBytes);
          } catch (IOException e) {
           JOptionPane.showMessageDialog(this, "Error reading image file: " + e.getMessage(), null, JOptionPane.ERROR_MESSAGE);
        return;
         }
        
        
        
        
        reportDao.submitReport(item,report,currentUser);
        
      
        
       nameField.setText("");
        dateField.setText("");
        locationField.setText("");
        descriptionArea.setText("");

        categories.setSelectedIndex(0);

        imageSpaceLbl.setIcon(null);
        imageSpaceLbl.setText("No image selected");

        originalImage = null;  
        
        
        
    }

    private void uploadImage() {

        fileChooser = new JFileChooser();

        int response = fileChooser.showOpenDialog(this);

        if (response == JFileChooser.APPROVE_OPTION) {
            
            File file = fileChooser.getSelectedFile();
            imagePath = file.getAbsolutePath();
            originalImage = new javax.swing.ImageIcon(imagePath);
            Image scaledImage = originalImage.getImage().getScaledInstance(320,180,Image.SCALE_SMOOTH);
            
            javax.swing.ImageIcon scaledIcon =new javax.swing.ImageIcon(scaledImage);
            imageSpaceLbl.setText("");
            imageSpaceLbl.setIcon(scaledIcon);
            
            Image prevImage = originalImage.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
            originalImage = new ImageIcon(prevImage);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
          

        if (e.getSource() == cancelBtn) {
            clearForm();
        } else if (e.getSource() == subBtn) {
            
            submitReport();
            
            
            
        } else if (e.getSource() == uploadBtn) {
            uploadImage();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource() == imageSpaceLbl && originalImage != null) {
            JLabel expandSpace = new JLabel(originalImage);

            expandSpace.setPreferredSize(new Dimension(800, 600));

            expandSpace.setBackground(Color.BLACK);
            expandSpace.setOpaque(true);

            JOptionPane.showMessageDialog(this,expandSpace,"Image Preview",JOptionPane.PLAIN_MESSAGE);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}