package com.company;

import Parts.OS;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateHtmlPages {

    public static void startCreate() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("projectdb.odb");
        EntityManager em = emf.createEntityManager();

        TypedQuery<Laptop> query = em.createQuery("SELECT laptop FROM Laptop laptop", Laptop.class);
        List<Laptop> allLaptops = query.getResultList();
        createHtmlFiles(allLaptops);
    }

    private static String getOsDisplay(OS i_Os) {
        String osString = i_Os.getManufacture();
        if (i_Os.getSeries() != 0)
            osString = osString + " " + i_Os.getSeries();

        if (i_Os.getVersion().equals("") == false && i_Os.getVersion().equals("0") == false)
            osString = osString + " " + i_Os.getVersion();

        if (i_Os.getBitSize() != 0)
            osString = osString + " " + i_Os.getBitSize() + "-bit";

        return osString;
    }

    private static String getFirstImagesString(ArrayList<String> i_Images) {

        String StringImagesFirst = "";
        if (i_Images.size() > 1) {
            for (int i = 1; i < i_Images.size(); i++) {
                StringImagesFirst = StringImagesFirst + "<div class=\"tab-pane\" id=\"pro-details" + (i + 1) + "\">\n" +
                        "                                            <div class=\"easyzoom easyzoom--overlay\">\n" +
                        "                                                <a href=\"" + i_Images.get(i) + "\">\n" +
                        "                                                    <img src=\"" + i_Images.get(i) + "\" alt=\"\">\n" +
                        "                                                </a>\n" +
                        "                                            </div>\n" +
                        "                                        </div>\n";
            }
        }
        return StringImagesFirst;
    }


    private static String getSecondImagesString(ArrayList<String> i_Images) {

        String StringImagesSecond = "";

        if (i_Images.size() > 1) {

            StringImagesSecond = "<div class=\"product-details-small nav mt-12 product-dec-slider owl-carousel\">\n" +
                    "                                        <a class=\"active\" href=\"#pro-details1\">\n" +
                    "                                            <img src=\"" + i_Images.get(0) + "\" alt=\"\">\n" +
                    "                                        </a>\n";

            for (int i = 1; i < i_Images.size(); i++) {
                StringImagesSecond = StringImagesSecond + "<a href=\"#pro-details" + (i + 1) + "\">\n" +
                        "                                            <img src=\"" + i_Images.get(i) + "\" alt=\"\">\n" +
                        "                                        </a>\n";
            }

            StringImagesSecond = StringImagesSecond + "</div>\n";

        }

        return StringImagesSecond;
    }


    private static void createHtmlFiles(List<Laptop> i_LaptopArray) {
        for (int i = 0; i < i_LaptopArray.size(); i++) {
            Laptop laptop = i_LaptopArray.get(i);
            String osDisplay = getOsDisplay(i_LaptopArray.get(i).getOperation_system());
            String touchScreenDisplay = "No";
            if (laptop.getTouch_screen()) touchScreenDisplay = "Yes";
            String ssdDisplay = "No";
            if (laptop.getStorage().getM_SSD()) ssdDisplay = "Yes";

            String StringImagesFirst = getFirstImagesString(laptop.getImagesUrls());

            String StringImagesSecond = getSecondImagesString(laptop.getImagesUrls());


            String html = "<!doctype html>\n" +
                    "<html class=\"no-js\" lang=\"zxx\">\n" +
                    "    <head>\n" +
                    "        <meta charset=\"utf-8\">\n" +
                    "        <meta http-equiv=\"x-ua-compatible\" content=\"ie=edge\">\n" +
                    "        <title>Oswan - eCommerce HTML5 Template</title>\n" +
                    "        <meta name=\"description\" content=\"Live Preview Of Oswan eCommerce HTML5 Template\">\n" +
                    "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                    "        <!-- Favicon -->\n" +
                    "        <link rel=\"shortcut icon\" type=\"image/x-icon\" href=\"assets/img/favicon.png\">\n" +
                    "\t\t\n" +
                    "\t\t<!-- all css here -->\n" +
                    "        <link rel=\"stylesheet\" href=\"assets/css/bootstrap.min.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"assets/css/animate.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"assets/css/owl.carousel.min.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"assets/css/chosen.min.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"assets/css/easyzoom.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"assets/css/meanmenu.min.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"assets/css/themify-icons.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"assets/css/icofont.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"assets/css/font-awesome.min.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"assets/css/bundle.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"assets/css/style.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"assets/css/responsive.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"product-details.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"footer.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"navbar.css\">\n" +
                    "\n" +
                    "        <script src=\"assets/js/vendor/modernizr-2.8.3.min.js\"></script>\n" +
                    "    </head>\n" +
                    "    <body>\n" +
                    "        <div class=\"wrapper\">\n" +
                    "            <header>\n" +
                    "                <div class=\"nav_container\">\n" +
                    "                    <nav>\n" +
                    "                        <input type=\"checkbox\" id=\"nav\" class=\"hidden\">\n" +
                    "                        <label for=\"nav\" class=\"nav-btn\">\n" +
                    "                            <i></i>\n" +
                    "                            <i></i>\n" +
                    "                            <i></i>\n" +
                    "                        </label>\n" +
                    "                        <span class=\"nav-logo\">\n" +
                    "                            <a href=\"#\">LaptoPlus</a>\n" +
                    "                        </span>\n" +
                    "                        <span class=\"nav-wrapper\">\n" +
                    "                            <ul>\n" +
                    "                                <li><a href=\"#\">Home</a></li>\n" +
                    "                                <li><a href=\"#\">OverView</a></li>\n" +
                    "                                <li><a href=\"#\">Prices</a></li>\n" +
                    "                                <li><a href=\"#\">Purchases</a></li>\n" +
                    "                            </ul>\n" +
                    "                        </span>\n" +
                    "                    </nav>\n" +
                    "                </div>\n" +
                    "            </header>\n" +
                    "            <div class=\"breadcrumb-area pt-255 pb-170\" style=\"background-image: url(assets/img/banner/banner-4.jpg)\">\n" +
                    "                <div class=\"container-fluid\">\n" +
                    "                    <div class=\"breadcrumb-content text-center\">\n" +
                    "                        <h2>Laptop details </h2>\n" +
                    "                        <ul>\n" +
                    "                            <li>\n" +
                    "                                <a href=\"#\">Companies</a>\n" +
                    "                            </li>\n" +
                    "                            <li>\n" +
                    "                                <a href=\"../" + laptop.getCompany_name() + ".html" + "\">" + laptop.getCompany_name() + "</a>\n" +
                    "                            </li>\n" +
                    "                            <li>" + laptop.getModel_name() + "</li>\n" +
                    "                        </ul>\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "            </div>\n" +
                    "            <div class=\"product-details-area fluid-padding-3 ptb-130\">\n" +
                    "                <div class=\"container-fluid\">\n" +
                    "                    <div class=\"row\">\n" +
                    "                        <div class=\"col-lg-6\">\n" +
                    "                            <div class=\"product-details-img-content\">\n" +
                    "                                <div class=\"product-details-tab mr-40\">\n" +
                    "                                    <div class=\"product-details-large tab-content\">\n" +
                    "                                        <div class=\"tab-pane active\" id=\"pro-details1\">\n" +
                    "                                            <div class=\"easyzoom easyzoom--overlay\">\n" +
                    "                                                <a href=\"" + laptop.getImagesUrls().get(0) + "\">\n" +
                    "                                                    <img src=\"" + laptop.getImagesUrls().get(0) + "\" alt=\"\">\n" +
                    "                                                </a>\n" +
                    "                                            </div>\n" +
                    "                                        </div>\n" +
                    StringImagesFirst +
                    "                                    </div>\n" +
                    StringImagesSecond +
                    "                                </div>\n" +
                    "                            </div>\n" +
                    "                        </div>\n" +
                    "                        <div class=\"col-lg-6\">\n" +
                    "                            <div class=\"product-details-content\">\n" +
                    "                                <h2>" + laptop.getModel_name() + "</h2>\n" +
                    "                                <br>\n" +
                    "                                <div class=\"product-price\">\n" +
                    "                                    <span>" + laptop.getPrice() + "$" + "</span>\n" +
                    "                                </div>\n" +
                    "                                <div class=\"product-overview\">\n" +
                    "                                    <h5 class=\"pd-sub-title\">Product Overview</h5>\n" +
                    "                                    <p>" + laptop.getDescription() + "</p><br>\n" +
                    "                                    \n" +
                    "                                    <h5 class=\"pd-sub-title\">Tech Specs</h5>\n" +
                    "                                    <h4>Operation System</h4>\n" +
                    "                                    <ul>\n" +
                    "                                    \t<li class=\"indention\">" + osDisplay + "</li>\n" +
                    "                                    </ul>\n" +
                    "                                    <br>\n" +
                    "                                    <h4>Processor</h4>\n" +
                    "                                    <span>\n" +
                    "                                        <ul>\n" +
                    "                                            <li class=\"indention\">" + laptop.getProcessor().getManufacture() + " " + laptop.getProcessor().getModel() + "</li>\n" +
                    "                                        </ul>\n" +
                    "                                    </span>\n" +
                    "                                    <br>\n" +
                    "                                    <h4>Display & Graphics</h4>\n" +
                    "                                    <ul>\n" +
                    "                                    \t<li class=\"indention\">Screen Size: " + laptop.getScreen_size() + "\"</li>\n" +
                    "                                    \t<li class=\"indention\">Touch Screen: " + touchScreenDisplay + "</li>\n" +
                    "                                    \t<li class=\"indention\">GPU: " + laptop.getGpu().getManufacture() + " " + laptop.getGpu().getModel() + "</li>\n" +
                    "                                    </ul>\n" +
                    "                                    <br>\n" +
                    "                                    <h4>Memory</h4>\n" +
                    "                                    <span>\n" +
                    "                                        <ul>\n" +
                    "                                            <li class=\"indention\">" + laptop.getMemory() + " GB</li>\n" +
                    "                                        </ul>\n" +
                    "                                    </span>\n" +
                    "                                    <br>\n" +
                    "                                    <h4>Storage</h4>\n" +
                    "                                    <span>\n" +
                    "                                        <ul>\n" +
                    "                                            <li class=\"indention\">Total Storage: " + laptop.getStorage().getM_GB() + " GB</li>\n" +
                    " <li class=\"indention\">SSD: " + ssdDisplay + "</li>\n" +
                    "                                        </ul>\n" +
                    "                                    </span>\n" +
                    "                                    <br>\n" +
                    "                                    <h4>Physical Characteristics</h4>\n" +
                    "                                    <span>\n" +
                    "                                        <ul>\n" +
                    "                                            <li class=\"indention\">Weight : " + laptop.getWeight() + " kg</li>\n" +
                    "                                        </ul>\n" +
                    "                                    </span>\n" +
                    "                                </div>\n" +
                    "                            </div>\n" +
                    "                        </div>\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "            </div>\n" +
                    "            <footer class=\"footer-area footer--light\">\n" +
                    "                <div class=\"footer-big\">\n" +
                    "                  <!-- start .container -->\n" +
                    "                  <div class=\"container\">\n" +
                    "                    <div class=\"row\">\n" +
                    "                      <div class=\"col-md-3 col-sm-12\">\n" +
                    "                        <div class=\"footer-widget\">\n" +
                    "                          <div class=\"widget-about\">\n" +
                    "                            <img src=\"http://placehold.it/250x80\" alt=\"\" class=\"img-fluid\">\n" +
                    "                            <ul class=\"contact-details\">\n" +
                    "                              <li>\n" +
                    "                                <span class=\"icon-earphones\"></span> Call Us:\n" +
                    "                                <a href=\"tel:+972545988464\">+972-545988464</a>\n" +
                    "                              </li>\n" +
                    "                              <li>\n" +
                    "                                <span class=\"icon-envelope-open\"></span>\n" +
                    "                                <a href=\"mailto:support@LaptoPlus.com\">support@LaptoPlus.com</a>\n" +
                    "                              </li>\n" +
                    "                            </ul>\n" +
                    "                          </div>\n" +
                    "                        </div>\n" +
                    "                        <!-- Ends: .footer-widget -->\n" +
                    "                      </div>\n" +
                    "                      <!-- end /.col-md-4 -->\n" +
                    "                      <div class=\"col-md-3 col-sm-4\">\n" +
                    "                        <div class=\"footer-widget\">\n" +
                    "                          <div class=\"footer-menu footer-menu--1\">\n" +
                    "                            <h4 class=\"footer-widget-title\">Popular Category</h4>\n" +
                    "                            <ul>\n" +
                    "                              <li>\n" +
                    "                                <a href=\"#\">Find your Laptop</a>\n" +
                    "                              </li>\n" +
                    "                              <li>\n" +
                    "                                <a href=\"#\">Laptop Companies</a>\n" +
                    "                              </li>\n" +
                    "                              <li>\n" +
                    "                                <a href=\"#\">Softwares</a>\n" +
                    "                              </li>\n" +
                    "                            </ul>\n" +
                    "                          </div>\n" +
                    "                          <!-- end /.footer-menu -->\n" +
                    "                        </div>\n" +
                    "                        <!-- Ends: .footer-widget -->\n" +
                    "                      </div>\n" +
                    "                      <!-- end /.col-md-3 -->\n" +
                    "              \n" +
                    "                      <div class=\"col-md-3 col-sm-4\">\n" +
                    "                        <div class=\"footer-widget\">\n" +
                    "                          <div class=\"footer-menu\">\n" +
                    "                            <h4 class=\"footer-widget-title\">Our Company</h4>\n" +
                    "                            <ul>\n" +
                    "                              <li>\n" +
                    "                                <a href=\"#\">About Us</a>\n" +
                    "                              </li>\n" +
                    "                              <li>\n" +
                    "                                <a href=\"#\">How It Works</a>\n" +
                    "                              </li>\n" +
                    "                              <li>\n" +
                    "                                <a href=\"#\">Testimonials</a>\n" +
                    "                              </li>\n" +
                    "                              <li>\n" +
                    "                                <a href=\"#\">Contact Us</a>\n" +
                    "                              </li>\n" +
                    "                            </ul>\n" +
                    "                          </div>\n" +
                    "                          <!-- end /.footer-menu -->\n" +
                    "                        </div>\n" +
                    "                        <!-- Ends: .footer-widget -->\n" +
                    "                      </div>\n" +
                    "                      <!-- end /.col-lg-3 -->\n" +
                    "\n" +
                    "              \n" +
                    "                    </div>\n" +
                    "                    <!-- end /.row -->\n" +
                    "                  </div>\n" +
                    "                  <!-- end /.container -->\n" +
                    "                </div>\n" +
                    "                <!-- end /.footer-big -->\n" +
                    "              \n" +
                    "                <div class=\"mini-footer\">\n" +
                    "                  <div class=\"container\">\n" +
                    "                    <div class=\"row\">\n" +
                    "                      <div class=\"col-md-12\">\n" +
                    "                        <div class=\"copyright-text\">\n" +
                    "                          <p>© 2019\n" +
                    "                            All rights reserved. Created by\n" +
                    "                            <a href=\"#\">LaptoPlus</a>\n" +
                    "                          </p>\n" +
                    "                        </div>\n" +
                    "                      </div>\n" +
                    "                    </div>\n" +
                    "                  </div>\n" +
                    "                </div>\n" +
                    "              </footer>\n" +
                    "        </div>\n" +
                    "\n" +
                    "\t\t<!-- all js here -->\n" +
                    "        <script src=\"assets/js/vendor/jquery-1.12.0.min.js\"></script>\n" +
                    "        <script src=\"assets/js/popper.js\"></script>\n" +
                    "        <script src=\"assets/js/bootstrap.min.js\"></script>\n" +
                    "        <script src=\"assets/js/isotope.pkgd.min.js\"></script>\n" +
                    "        <script src=\"assets/js/imagesloaded.pkgd.min.js\"></script>\n" +
                    "        <script src=\"assets/js/jquery.counterup.min.js\"></script>\n" +
                    "        <script src=\"assets/js/waypoints.min.js\"></script>\n" +
                    "        <script src=\"assets/js/owl.carousel.min.js\"></script>\n" +
                    "        <script src=\"assets/js/plugins.js\"></script>\n" +
                    "        <script src=\"assets/js/main.js\"></script>\n" +
                    "        <script src=\"product-details.js\"></script>\n" +
                    "    </body>\n" +
                    "</html>\n" +
                    "\n";
            File newHtmlFile = new File("laptops/" + i + ".html");
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(newHtmlFile));
                bw.write(html);
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}

