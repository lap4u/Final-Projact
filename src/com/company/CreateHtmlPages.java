package com.company;

import Parts.OS;
import Softwares.SoftwareGame;

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

    public static void startCreateGamesSoftwares() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("projectdb.odb");
        EntityManager em = emf.createEntityManager();
        TypedQuery<SoftwareGame> query2 = em.createQuery("SELECT softwareGame FROM SoftwareGame softwareGame", SoftwareGame.class);
        List<SoftwareGame> allSoftwareGames = query2.getResultList();
        createGamesSoftwaresHtmlFiles(allSoftwareGames);
    }

    public static void startCreateLaptops()
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("projectdb.odb");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Laptop> query = em.createQuery("SELECT laptop FROM Laptop laptop", Laptop.class);
        List<Laptop> allLaptops = query.getResultList();
        createLaptopsHtmlFiles(allLaptops);
    }

    public static void startCreateCompanyPage(String i_CompanyName)
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("projectdb.odb");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Laptop> query = em.createQuery("SELECT laptop FROM Laptop laptop WHERE laptop.company_name=:i_CompanyName", Laptop.class);
        query.setParameter("i_CompanyName", i_CompanyName);
        List<Laptop> companyLaptops = query.getResultList();
        System.out.println(companyLaptops.size());
        createCompanyPageHtml(i_CompanyName,companyLaptops);
    }

    public static void startCreateGamesSoftwaresPage(boolean isGame)
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("projectdb.odb");
        EntityManager em = emf.createEntityManager();
        TypedQuery<SoftwareGame> query = em.createQuery("SELECT softwareGame FROM SoftwareGame softwareGame WHERE SoftwareGame.isGame=:isGame", SoftwareGame.class);
        query.setParameter("isGame", isGame);
        List<SoftwareGame> softwareGamesList = query.getResultList();
        System.out.println(softwareGamesList.size());
        createMainGamesSoftwaresHtml(isGame, softwareGamesList);
    }

    private static void createMainGamesSoftwaresHtml(boolean isGame, List<SoftwareGame> i_SoftwaresGames)
    {
        String gameSoftwaresSquareHtml = createSquaresGameSoftwares(i_SoftwaresGames);
        String type = "Software";
        if(isGame == true)
            type = "Game";

        String html = "<!doctype html>\n" +
                "<html class=\"no-js\" lang=\"zxx\">\n" +
                "    <head>\n" +
                "        <meta charset=\"utf-8\">\n" +
                "        <meta http-equiv=\"x-ua-compatible\" content=\"ie=edge\">\n" +
                "        <title>LaptoPlus - "+ type +"s List</title>\n" +
                "        <meta name=\"description\" content=\"LaptoPlus - "+ type+"s List\">\n" +
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "        <!-- Favicon -->\n" +
                "        <link rel=\"shortcut icon\" type=\"image/x-icon\" href=\"assets/img/favicon.png\">\n" +
                "\t\t\n" +
                "\t\t<!-- all css here -->\n" +
                "        <link rel=\"stylesheet\" href=\"assets/css/bootstrap.min.css\">\n" +
                "        <link rel=\"stylesheet\" href=\"assets/css/animate.css\">\n" +
                "        <link rel=\"stylesheet\" href=\"assets/css/owl.carousel.min.css\">\n" +
                "        <link rel=\"stylesheet\" href=\"assets/css/chosen.min.css\">\n" +
                "        <link rel=\"stylesheet\" href=\"assets/css/jquery-ui.css\">\n" +
                "        <link rel=\"stylesheet\" href=\"assets/css/meanmenu.min.css\">\n" +
                "        <link rel=\"stylesheet\" href=\"assets/css/themify-icons.css\">\n" +
                "        <link rel=\"stylesheet\" href=\"assets/css/icofont.css\">\n" +
                "        <link rel=\"stylesheet\" href=\"assets/css/font-awesome.min.css\">\n" +
                "        <link rel=\"stylesheet\" href=\"assets/css/bundle.css\">\n" +
                "        <link rel=\"stylesheet\" href=\"assets/css/style.css\">\n" +
                "        <link rel=\"stylesheet\" href=\"assets/css/responsive.css\">\n" +
                "        <link rel=\"stylesheet\" href=\"assets/footer.css\">\n" +
                "        <link rel=\"stylesheet\" href=\"assets/navbar.css\">\n" +
                "        <script src=\"assets/js/vendor/modernizr-2.8.3.min.js\"></script>\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <div class=\"wrapper\">\n" +
                "            <!-- header start -->\n" +
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
                "            <div class=\"breadcrumb-area pt-255 pb-170\" style=\"background-image: url(assets/img/banner/"+ type +"s.jpg)\">\n" +
                "                <div class=\"container-fluid\">\n" +
                "                    <div class=\"breadcrumb-content text-center\">\n" +
                "                        <h2>"+ type +"s List</h2>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div class=\"shop-wrapper fluid-padding-2 pt-120 pb-150\">\n" +
                "                <div class=\"container-fluid\">\n" +
                "                    <div class=\"row\">\n" +
                "                        <div class=\"col-lg-1\">\n" +
                "                            \n" +
                "                        </div>\n" +
                "                        <div class=\"col-lg-10\">\n" +
                "                            <div class=\"shop-topbar-wrapper\">\n" +
                "                                <div class=\"grid-list-options\">\n" +
                "                                    <ul class=\"view-mode\">\n" +
                "                                        <li class=\"active\"><a href=\"#product-grid\" data-view=\"product-grid\"><i class=\"ti-layout-grid2\"></i></a></li>\n" +
                "                                        <li><a href=\"#product-list\" data-view=\"product-list\"><i class=\"ti-view-list\"></i></a></li>\n" +
                "                                    </ul>\n" +
                "                                </div>\n" +
                "                                <div class=\"product-sorting\">\n" +
                "                                    <div class=\"sorting sorting-bg-1\">\n" +
                "                                        <form>\n" +
                "                                            <select class=\"select\">\n" +
                "                                                <option value=\"\">Default softing </option>\n" +
                "                                                <option value=\"\">Sort by news</option>\n" +
                "                                                <option value=\"\">Sort by price</option>\n" +
                "                                            </select>\n" +
                "                                        </form>\n" +
                "                                    </div>\n" +
                "                                </div>\n" +
                "                            </div>\n" +
                "                            <div class=\"grid-list-product-wrapper tab-content\">\n" +
                "                                <div id=\"new-product\" class=\"product-grid product-view tab-pane active\">\n" +
                "                                    <div class=\"row\">\n" +
                gameSoftwaresSquareHtml +
                "                                    </div>\n" +
                "                                </div>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                        <div class=\"col-lg-1\">\n" +
                "                            \n" +
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
                "            <!-- modal -->\n" +
                "            <div class=\"modal fade\" id=\"exampleModal\" tabindex=\"-1\" role=\"dialog\" aria-hidden=\"true\">\n" +
                "                <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\">\n" +
                "                    <span class=\"icofont icofont-close\" aria-hidden=\"true\"></span>\n" +
                "                </button>\n" +
                "                <div class=\"modal-dialog\" role=\"document\">\n" +
                "                    <div class=\"modal-content\">\n" +
                "                        <div class=\"modal-body\">\n" +
                "                            <div class=\"qwick-view-left\">\n" +
                "                                <div class=\"quick-view-learg-img\">\n" +
                "                                    <div class=\"quick-view-tab-content tab-content\">\n" +
                "                                        <div class=\"tab-pane active show fade\" id=\"modal1\" role=\"tabpanel\">\n" +
                "                                            <img src=\"assets/img/quick-view/l1.jpg\" alt=\"\">\n" +
                "                                        </div>\n" +
                "                                        <div class=\"tab-pane fade\" id=\"modal2\" role=\"tabpanel\">\n" +
                "                                            <img src=\"assets/img/quick-view/l2.jpg\" alt=\"\">\n" +
                "                                        </div>\n" +
                "                                        <div class=\"tab-pane fade\" id=\"modal3\" role=\"tabpanel\">\n" +
                "                                            <img src=\"assets/img/quick-view/l3.jpg\" alt=\"\">\n" +
                "                                        </div>\n" +
                "                                    </div>\n" +
                "                                </div>\n" +
                "                                <div class=\"quick-view-list nav\" role=\"tablist\">\n" +
                "                                    <a class=\"active\" href=\"#modal1\" data-toggle=\"tab\" role=\"tab\">\n" +
                "                                        <img src=\"assets/img/quick-view/s1.jpg\" alt=\"\">\n" +
                "                                    </a>\n" +
                "                                    <a href=\"#modal2\" data-toggle=\"tab\" role=\"tab\">\n" +
                "                                        <img src=\"assets/img/quick-view/s2.jpg\" alt=\"\">\n" +
                "                                    </a>\n" +
                "                                    <a href=\"#modal3\" data-toggle=\"tab\" role=\"tab\">\n" +
                "                                        <img src=\"assets/img/quick-view/s3.jpg\" alt=\"\">\n" +
                "                                    </a>\n" +
                "                                </div>\n" +
                "                            </div>\n" +
                "                            <div class=\"qwick-view-right\">\n" +
                "                                <div class=\"qwick-view-content\">\n" +
                "                                    <h3>Aeri Carbon Helmet</h3>\n" +
                "                                    <div class=\"price\">\n" +
                "                                        <span class=\"new\">$90.00</span>\n" +
                "                                        <span class=\"old\">$120.00  </span>\n" +
                "                                    </div>\n" +
                "                                    <div class=\"rating-number\">\n" +
                "                                        <div class=\"quick-view-rating\">\n" +
                "                                            <i class=\"fa fa-star reting-color\"></i>\n" +
                "                                            <i class=\"fa fa-star reting-color\"></i>\n" +
                "                                            <i class=\"fa fa-star reting-color\"></i>\n" +
                "                                            <i class=\"fa fa-star reting-color\"></i>\n" +
                "                                            <i class=\"fa fa-star reting-color\"></i>\n" +
                "                                        </div>\n" +
                "                                    </div>\n" +
                "                                    <p>Lorem ipsum dolor sit amet, consectetur adip elit, sed do tempor incididun ut labore et dolore magna aliqua. Ut enim ad mi , quis nostrud veniam exercitation .</p>\n" +
                "                                    <div class=\"quick-view-select\">\n" +
                "                                        <div class=\"select-option-part\">\n" +
                "                                            <label>Size*</label>\n" +
                "                                            <select class=\"select\">\n" +
                "                                                <option value=\"\">- Please Select -</option>\n" +
                "                                                <option value=\"\">900</option>\n" +
                "                                                <option value=\"\">700</option>\n" +
                "                                            </select>\n" +
                "                                        </div>\n" +
                "                                        <div class=\"select-option-part\">\n" +
                "                                            <label>Color*</label>\n" +
                "                                            <select class=\"select\">\n" +
                "                                                <option value=\"\">- Please Select -</option>\n" +
                "                                                <option value=\"\">orange</option>\n" +
                "                                                <option value=\"\">pink</option>\n" +
                "                                                <option value=\"\">yellow</option>\n" +
                "                                            </select>\n" +
                "                                        </div>\n" +
                "                                    </div>\n" +
                "                                    <div class=\"quickview-plus-minus\">\n" +
                "                                        <div class=\"cart-plus-minus\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<input type=\"text\" value=\"02\" name=\"qtybutton\" class=\"cart-plus-minus-box\">\n" +
                "\t\t\t\t\t\t\t\t\t\t</div>\n" +
                "                                        <div class=\"quickview-btn-cart\">\n" +
                "                                            <a class=\"btn-style\" href=\"#\">add to cart</a>\n" +
                "                                        </div>\n" +
                "                                        <div class=\"quickview-btn-wishlist\">\n" +
                "                                            <a class=\"btn-hover\" href=\"#\"><i class=\"icofont icofont-heart-alt\"></i></a>\n" +
                "                                        </div>\n" +
                "                                    </div>\n" +
                "                                </div>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        \n" +
                "        \n" +
                "        \n" +
                "\t\t\n" +
                "\t\t\n" +
                "\t\t\n" +
                "\t\t\n" +
                "\t\t\n" +
                "\t\t\n" +
                "\t\t\n" +
                "\t\t\n" +
                "\t\t<!-- all js here -->\n" +
                "        <script src=\"assets/js/vendor/jquery-1.12.0.min.js\"></script>\n" +
                "        <script src=\"assets/js/popper.js\"></script>\n" +
                "        <script src=\"assets/js/bootstrap.min.js\"></script>\n" +
                "        <script src=\"assets/js/isotope.pkgd.min.js\"></script>\n" +
                "        <script src=\"assets/js/imagesloaded.pkgd.min.js\"></script>\n" +
                "        <script src=\"assets/js/jquery.counterup.min.js\"></script>\n" +
                "        <script src=\"assets/js/waypoints.min.js\"></script>\n" +
                "        \n" +
                "        <script src=\"assets/js/owl.carousel.min.js\"></script>\n" +
                "        <script src=\"assets/js/plugins.js\"></script>\n" +
                "        <script src=\"assets/js/main.js\"></script>\n" +
                "    </body>\n" +
                "</html>";

        File newHtmlFile;
        if(isGame == true)
           newHtmlFile = new File("gameslist.html");
        else
            newHtmlFile = new File("softwareslist.html");
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(newHtmlFile));
            bw.write(html);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String createSquaresGameSoftwares(List<SoftwareGame> i_GameSoftwares)
    {
        String softwareGameSquareHtml = "";
        String model_name;
        for(int i=i_GameSoftwares.size()-1;i>=0;i--) {
            SoftwareGame softwareGame = i_GameSoftwares.get(i);

            softwareGameSquareHtml = softwareGameSquareHtml + "<div class=\"product-width col-md-6 col-xl-4 col-lg-6\">\n" +
                    "                                            <div class=\"product-wrapper mb-35\">\n" +
                    "                                                <div class=\"product-img\">\n" +
                    "                                                    <a href=\"gamesoftwares/"+softwareGame.getId()+".html\">\n" +
                    "                                                        <img src=\""+ softwareGame.getImgURL()+"\" alt=\"\">\n" +
                    "                                                    </a>\n" +
                    "                                                    <div class=\"product-content-wrapper\">\n" +
                    "                                                        <div class=\"product-title-spreed\">\n" +
                    "                                                            <h4><a href=\"gamesoftwares/"+softwareGame.getId()+".html\">"+softwareGame.getName()+"</a></h4>\n" +
                    "                                                           \n" +
                    "                                                        </div>\n" +
                    "                                                    </div>\n" +
                    "                                                </div>\n" +
                    "                                                <div class=\"product-list-details\">\n" +
                    "                                                    <h2><a href=\"gamesoftwares/"+softwareGame.getId()+".html\">"+ softwareGame.getName() +"</a></h2>\n" +
                    "                                                    <div class=\"quick-view-rating\">\n" +
                    "                                                        <i class=\"fa fa-star reting-color\"></i>\n" +
                    "                                                        <i class=\"fa fa-star reting-color\"></i>\n" +
                    "                                                        <i class=\"fa fa-star reting-color\"></i>\n" +
                    "                                                        <i class=\"fa fa-star reting-color\"></i>\n" +
                    "                                                        <i class=\"fa fa-star reting-color\"></i>\n" +
                    "                                                    </div>\n" +
                    "                                                    <p>"+ softwareGame.getDescription()+"</p>\n" +
                    "                                                </div>\n" +
                    "                                            </div>\n" +
                    "                                        </div>";
        }

        return softwareGameSquareHtml;
    }


    private static String createSquaresLaptops(List<Laptop> i_CompanyLaptops)
    {
        String laptopsSquareHtml = "";
        String model_name;
        for(int i=0;i<i_CompanyLaptops.size();i++) {
            Laptop laptop = i_CompanyLaptops.get(i);
            model_name = laptop.getModel_name();
            if(laptop.getCompany_name().equals("LG"))
              model_name = laptop.getModel_name().substring(0, laptop.getModel_name().indexOf("Laptop"));

            String priceString = "$" + String.valueOf ((int) laptop.getPrice());
            if(laptop.getPrice() == 0)
                priceString = "";



            laptopsSquareHtml = laptopsSquareHtml + "<div class=\"product-width col-md-6 col-xl-4 col-lg-6\">\n" +
                    "                                            <div class=\"product-wrapper mb-35\">\n" +
                    "                                                <div class=\"product-img\">\n" +
                    "                                                    <a href=\"../laptops/"+laptop.getId_prod()+".html\">\n" +
                    "                                                        <img src=\""+ laptop.getImagesUrls().get(0)+"\" alt=\"\">\n" +
                    "                                                    </a>\n" +
                    "                                                    <div class=\"product-content-wrapper\">\n" +
                    "                                                        <div class=\"product-title-spreed\">\n" +
                    "                                                            <h4><a href=\"../laptops/"+laptop.getId_prod()+".html\">"+model_name+"</a></h4>\n" +
                    "                                                           \n" +
                    "                                                        </div>\n" +
                    "                                                        <div class=\"product-price\">\n" +
                    "                                                            <span>"+priceString+"</span>\n" +
                    "                                                        </div>\n" +
                    "                                                    </div>\n" +
                    "                                                </div>\n" +
                    "                                                <div class=\"product-list-details\">\n" +
                    "                                                    <h2><a href=\"../laptops/"+laptop.getId_prod()+".html\">"+ laptop.getModel_name() +"</a></h2>\n" +
                    "                                                    <div class=\"quick-view-rating\">\n" +
                    "                                                        <i class=\"fa fa-star reting-color\"></i>\n" +
                    "                                                        <i class=\"fa fa-star reting-color\"></i>\n" +
                    "                                                        <i class=\"fa fa-star reting-color\"></i>\n" +
                    "                                                        <i class=\"fa fa-star reting-color\"></i>\n" +
                    "                                                        <i class=\"fa fa-star reting-color\"></i>\n" +
                    "                                                    </div>\n" +
                    "                                                    <div class=\"product-price\">\n" +
                    "                                                        <span>"+priceString+"</span>\n" +
                    "                                                    </div>\n" +
                    "                                                    <p>"+laptop.getDescription()+"</p>\n" +
                    "                                                </div>\n" +
                    "                                            </div>\n" +
                    "                                        </div>";
        }

        return laptopsSquareHtml;
    }

    private static void createCompanyPageHtml(String i_CompanyName, List<Laptop> i_CompanyLaptops)
    {
        String laptopsSquareHtml = createSquaresLaptops(i_CompanyLaptops);

        String html = "<!doctype html>\n" +
                "<html class=\"no-js\" lang=\"zxx\">\n" +
                "    <head>\n" +
                "        <meta charset=\"utf-8\">\n" +
                "        <meta http-equiv=\"x-ua-compatible\" content=\"ie=edge\">\n" +
                "        <title>LaptoPlus - Companies - "+ i_CompanyName+"</title>\n" +
                "        <meta name=\"description\" content=\"LaptoPlus - Companies - \"+ i_CompanyName+\"\">\n" +
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "        <!-- Favicon -->\n" +
                "        <link rel=\"shortcut icon\" type=\"image/x-icon\" href=\"../assets/img/favicon.png\">\n" +
                "\t\t\n" +
                "\t\t<!-- all css here -->\n" +
                "        <link rel=\"stylesheet\" href=\"../assets/css/bootstrap.min.css\">\n" +
                "        <link rel=\"stylesheet\" href=\"../assets/css/animate.css\">\n" +
                "        <link rel=\"stylesheet\" href=\"../assets/css/owl.carousel.min.css\">\n" +
                "        <link rel=\"stylesheet\" href=\"../assets/css/chosen.min.css\">\n" +
                "        <link rel=\"stylesheet\" href=\"../assets/css/jquery-ui.css\">\n" +
                "        <link rel=\"stylesheet\" href=\"../assets/css/meanmenu.min.css\">\n" +
                "        <link rel=\"stylesheet\" href=\"../assets/css/themify-icons.css\">\n" +
                "        <link rel=\"stylesheet\" href=\"../assets/css/icofont.css\">\n" +
                "        <link rel=\"stylesheet\" href=\"../assets/css/font-awesome.min.css\">\n" +
                "        <link rel=\"stylesheet\" href=\"../assets/css/bundle.css\">\n" +
                "        <link rel=\"stylesheet\" href=\"../assets/css/style.css\">\n" +
                "        <link rel=\"stylesheet\" href=\"../assets/css/responsive.css\">\n" +
                "        <link rel=\"stylesheet\" href=\"../assets/footer.css\">\n" +
                "        <link rel=\"stylesheet\" href=\"../assets/navbar.css\">\n" +
                "        <script src=\"../assets/js/vendor/modernizr-2.8.3.min.js\"></script>\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <div class=\"wrapper\">\n" +
                "            <!-- header start -->\n" +
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
                "            <div class=\"breadcrumb-area pt-255 pb-170\" style=\"background-image: url(../assets/img/banner/"+ i_CompanyName +".jpg)\">\n" +
                "                <div class=\"container-fluid\">\n" +
                "                    <div class=\"breadcrumb-content text-center\">\n" +
                "                        <h2>"+ i_CompanyName +"</h2>\n" +
                "                        <ul>\n" +
                "                            <li>\n" +
                "                                <a href=\"../companies.html\">Companies</a>\n" +
                "                            </li>\n" +
                "                            <li>"+ i_CompanyName+"</li>\n" +
                "                        </ul>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div class=\"shop-wrapper fluid-padding-2 pt-120 pb-150\">\n" +
                "                <div class=\"container-fluid\">\n" +
                "                    <div class=\"row\">\n" +
                "                        <div class=\"col-lg-1\">\n" +
                "                            \n" +
                "                        </div>\n" +
                "                        <div class=\"col-lg-10\">\n" +
                "                            <div class=\"shop-topbar-wrapper\">\n" +
                "                                <div class=\"grid-list-options\">\n" +
                "                                    <ul class=\"view-mode\">\n" +
                "                                        <li class=\"active\"><a href=\"#product-grid\" data-view=\"product-grid\"><i class=\"ti-layout-grid2\"></i></a></li>\n" +
                "                                        <li><a href=\"#product-list\" data-view=\"product-list\"><i class=\"ti-view-list\"></i></a></li>\n" +
                "                                    </ul>\n" +
                "                                </div>\n" +
                "                                <div class=\"product-sorting\">\n" +
                "                                    <div class=\"sorting sorting-bg-1\">\n" +
                "                                        <form>\n" +
                "                                            <select class=\"select\">\n" +
                "                                                <option value=\"\">Default softing </option>\n" +
                "                                                <option value=\"\">Sort by news</option>\n" +
                "                                                <option value=\"\">Sort by price</option>\n" +
                "                                            </select>\n" +
                "                                        </form>\n" +
                "                                    </div>\n" +
                "                                </div>\n" +
                "                            </div>\n" +
                "                            <div class=\"grid-list-product-wrapper tab-content\">\n" +
                "                                <div id=\"new-product\" class=\"product-grid product-view tab-pane active\">\n" +
                "                                    <div class=\"row\">\n" +
                laptopsSquareHtml +
                "                                    </div>\n" +
                "                                </div>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                        <div class=\"col-lg-1\">\n" +
                "                            \n" +
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
                "            <!-- modal -->\n" +
                "            <div class=\"modal fade\" id=\"exampleModal\" tabindex=\"-1\" role=\"dialog\" aria-hidden=\"true\">\n" +
                "                <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\">\n" +
                "                    <span class=\"icofont icofont-close\" aria-hidden=\"true\"></span>\n" +
                "                </button>\n" +
                "                <div class=\"modal-dialog\" role=\"document\">\n" +
                "                    <div class=\"modal-content\">\n" +
                "                        <div class=\"modal-body\">\n" +
                "                            <div class=\"qwick-view-left\">\n" +
                "                                <div class=\"quick-view-learg-img\">\n" +
                "                                    <div class=\"quick-view-tab-content tab-content\">\n" +
                "                                        <div class=\"tab-pane active show fade\" id=\"modal1\" role=\"tabpanel\">\n" +
                "                                            <img src=\"../assets/img/quick-view/l1.jpg\" alt=\"\">\n" +
                "                                        </div>\n" +
                "                                        <div class=\"tab-pane fade\" id=\"modal2\" role=\"tabpanel\">\n" +
                "                                            <img src=\"../assets/img/quick-view/l2.jpg\" alt=\"\">\n" +
                "                                        </div>\n" +
                "                                        <div class=\"tab-pane fade\" id=\"modal3\" role=\"tabpanel\">\n" +
                "                                            <img src=\"../assets/img/quick-view/l3.jpg\" alt=\"\">\n" +
                "                                        </div>\n" +
                "                                    </div>\n" +
                "                                </div>\n" +
                "                                <div class=\"quick-view-list nav\" role=\"tablist\">\n" +
                "                                    <a class=\"active\" href=\"#modal1\" data-toggle=\"tab\" role=\"tab\">\n" +
                "                                        <img src=\"../assets/img/quick-view/s1.jpg\" alt=\"\">\n" +
                "                                    </a>\n" +
                "                                    <a href=\"#modal2\" data-toggle=\"tab\" role=\"tab\">\n" +
                "                                        <img src=\"../assets/img/quick-view/s2.jpg\" alt=\"\">\n" +
                "                                    </a>\n" +
                "                                    <a href=\"#modal3\" data-toggle=\"tab\" role=\"tab\">\n" +
                "                                        <img src=\"../assets/img/quick-view/s3.jpg\" alt=\"\">\n" +
                "                                    </a>\n" +
                "                                </div>\n" +
                "                            </div>\n" +
                "                            <div class=\"qwick-view-right\">\n" +
                "                                <div class=\"qwick-view-content\">\n" +
                "                                    <h3>Aeri Carbon Helmet</h3>\n" +
                "                                    <div class=\"price\">\n" +
                "                                        <span class=\"new\">$90.00</span>\n" +
                "                                        <span class=\"old\">$120.00  </span>\n" +
                "                                    </div>\n" +
                "                                    <div class=\"rating-number\">\n" +
                "                                        <div class=\"quick-view-rating\">\n" +
                "                                            <i class=\"fa fa-star reting-color\"></i>\n" +
                "                                            <i class=\"fa fa-star reting-color\"></i>\n" +
                "                                            <i class=\"fa fa-star reting-color\"></i>\n" +
                "                                            <i class=\"fa fa-star reting-color\"></i>\n" +
                "                                            <i class=\"fa fa-star reting-color\"></i>\n" +
                "                                        </div>\n" +
                "                                    </div>\n" +
                "                                    <p>Lorem ipsum dolor sit amet, consectetur adip elit, sed do tempor incididun ut labore et dolore magna aliqua. Ut enim ad mi , quis nostrud veniam exercitation .</p>\n" +
                "                                    <div class=\"quick-view-select\">\n" +
                "                                        <div class=\"select-option-part\">\n" +
                "                                            <label>Size*</label>\n" +
                "                                            <select class=\"select\">\n" +
                "                                                <option value=\"\">- Please Select -</option>\n" +
                "                                                <option value=\"\">900</option>\n" +
                "                                                <option value=\"\">700</option>\n" +
                "                                            </select>\n" +
                "                                        </div>\n" +
                "                                        <div class=\"select-option-part\">\n" +
                "                                            <label>Color*</label>\n" +
                "                                            <select class=\"select\">\n" +
                "                                                <option value=\"\">- Please Select -</option>\n" +
                "                                                <option value=\"\">orange</option>\n" +
                "                                                <option value=\"\">pink</option>\n" +
                "                                                <option value=\"\">yellow</option>\n" +
                "                                            </select>\n" +
                "                                        </div>\n" +
                "                                    </div>\n" +
                "                                    <div class=\"quickview-plus-minus\">\n" +
                "                                        <div class=\"cart-plus-minus\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<input type=\"text\" value=\"02\" name=\"qtybutton\" class=\"cart-plus-minus-box\">\n" +
                "\t\t\t\t\t\t\t\t\t\t</div>\n" +
                "                                        <div class=\"quickview-btn-cart\">\n" +
                "                                            <a class=\"btn-style\" href=\"#\">add to cart</a>\n" +
                "                                        </div>\n" +
                "                                        <div class=\"quickview-btn-wishlist\">\n" +
                "                                            <a class=\"btn-hover\" href=\"#\"><i class=\"icofont icofont-heart-alt\"></i></a>\n" +
                "                                        </div>\n" +
                "                                    </div>\n" +
                "                                </div>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        \n" +
                "        \n" +
                "        \n" +
                "\t\t\n" +
                "\t\t\n" +
                "\t\t\n" +
                "\t\t\n" +
                "\t\t\n" +
                "\t\t\n" +
                "\t\t\n" +
                "\t\t\n" +
                "\t\t<!-- all js here -->\n" +
                "        <script src=\"../assets/js/vendor/jquery-1.12.0.min.js\"></script>\n" +
                "        <script src=\"../assets/js/popper.js\"></script>\n" +
                "        <script src=\"../assets/js/bootstrap.min.js\"></script>\n" +
                "        <script src=\"../assets/js/isotope.pkgd.min.js\"></script>\n" +
                "        <script src=\"../assets/js/imagesloaded.pkgd.min.js\"></script>\n" +
                "        <script src=\"../assets/js/jquery.counterup.min.js\"></script>\n" +
                "        <script src=\"../assets/js/waypoints.min.js\"></script>\n" +
                "        \n" +
                "        <script src=\"../assets/js/owl.carousel.min.js\"></script>\n" +
                "        <script src=\"../assets/js/plugins.js\"></script>\n" +
                "        <script src=\"../assets/js/main.js\"></script>\n" +
                "    </body>\n" +
                "</html>";

        File newHtmlFile = new File("companies/" + i_CompanyName + ".html");
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(newHtmlFile));
            bw.write(html);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private static void createGamesSoftwaresHtmlFiles(List<SoftwareGame> i_SoftwareGamesArray)
    {
        for (int i = 0; i < i_SoftwareGamesArray.size(); i++) {
            SoftwareGame softwareGame = i_SoftwareGamesArray.get(i);
            String osDisplay = getOsDisplay(i_SoftwareGamesArray.get(i).getOperatingSystem());
            String typeProduct = "Software";
            if(softwareGame.isGame() == true)
                typeProduct = "Game";

            String html = "<!doctype html>\n" +
                    "<html class=\"no-js\" lang=\"zxx\">\n" +
                    "    <head>\n" +
                    "        <meta charset=\"utf-8\">\n" +
                    "        <meta http-equiv=\"x-ua-compatible\" content=\"ie=edge\">\n" +
                    "        <title>LaptoPlus - "+typeProduct+" - "+softwareGame.getName()+"</title>\n" +
                    "        <meta name=\"description\" content=\""+softwareGame.getDescription()+"\">\n" +
                    "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                    "        <!-- Favicon -->\n" +
                    "        <link rel=\"shortcut icon\" type=\"image/x-icon\" href=\"../assets/img/favicon.png\">\n" +
                    "\t\t\n" +
                    "\t\t<!-- all css here -->\n" +
                    "        <link rel=\"stylesheet\" href=\"../assets/css/bootstrap.min.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"../assets/css/animate.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"../assets/css/owl.carousel.min.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"../assets/css/chosen.min.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"../assets/css/easyzoom.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"../assets/css/meanmenu.min.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"../assets/css/themify-icons.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"../assets/css/icofont.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"../assets/css/font-awesome.min.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"../assets/css/bundle.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"../assets/css/style.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"../assets/css/responsive.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"../assets/product-details.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"../assets/footer.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"../assets/navbar.css\">\n" +
                    "\n" +
                    "        <script src=\"../assets/js/vendor/modernizr-2.8.3.min.js\"></script>\n" +
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
                    "            <div class=\"breadcrumb-area pt-255 pb-170\" style=\"background-image: url(../assets/img/banner/games.jpg)\">\n" +
                    "                <div class=\"container-fluid\">\n" +
                    "                    <div class=\"breadcrumb-content text-center\">\n" +
                    "                        <h2>" + typeProduct + " details </h2>\n" +
                    "                        <ul>\n" +
                    "                            <li>" + softwareGame.getName() + "</li>\n" +
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
                    "                                                <a href=\"" + softwareGame.getImgURL() + "\">\n" +
                    "                                                    <img src=\"" + softwareGame.getImgURL() + "\" alt=\"\">\n" +
                    "                                                </a>\n" +
                    "                                            </div>\n" +
                    "                                        </div>\n" +
                    "                                    </div>\n" +
                    "                                </div>\n" +
                    "                            </div>\n" +
                    "                        </div>\n" +
                    "                        <div class=\"col-lg-6\">\n" +
                    "                            <div class=\"product-details-content\">\n" +
                    "                                <h2>" + softwareGame.getName() + "</h2>\n" +
                    "                                <br>\n" +
                    "                                <div class=\"product-overview\">\n" +
                    "                                    <h5 class=\"pd-sub-title\">Product Overview</h5>\n" +
                    "                                    <p>" + softwareGame.getDescription() + "</p><br>\n" +
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
                    "                                            <li class=\"indention\">" + softwareGame.getProcessor().getManufacture() + " " + softwareGame.getProcessor().getModel() + "</li>\n" +
                    "                                        </ul>\n" +
                    "                                    </span>\n" +
                    "                                    <br>\n" +
                    "                                    <h4>Graphics</h4>\n" +
                    "                                    <ul>\n" +
                    "                                    \t<li class=\"indention\">" + softwareGame.getGpu().getManufacture() + " " + softwareGame.getGpu().getModel() + "</li>\n" +
                    "                                    </ul>\n" +
                    "                                    <br>\n" +
                    "                                    <h4>Memory</h4>\n" +
                    "                                    <span>\n" +
                    "                                        <ul>\n" +
                    "                                            <li class=\"indention\">" + softwareGame.getMemory() + " GB</li>\n" +
                    "                                        </ul>\n" +
                    "                                    </span>\n" +
                    "                                    <br>\n" +
                    "                                    <h4>Storage</h4>\n" +
                    "                                    <span>\n" +
                    "                                        <ul>\n" +
                    "                                            <li class=\"indention\">" + softwareGame.getHardDrive() + " GB</li>\n" +
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
                    "        <script src=\"../assets/js/vendor/jquery-1.12.0.min.js\"></script>\n" +
                    "        <script src=\"../assets/js/popper.js\"></script>\n" +
                    "        <script src=\"../assets/js/bootstrap.min.js\"></script>\n" +
                    "        <script src=\"../assets/js/isotope.pkgd.min.js\"></script>\n" +
                    "        <script src=\"../assets/js/imagesloaded.pkgd.min.js\"></script>\n" +
                    "        <script src=\"../assets/js/jquery.counterup.min.js\"></script>\n" +
                    "        <script src=\"../assets/js/waypoints.min.js\"></script>\n" +
                    "        <script src=\"../assets/js/owl.carousel.min.js\"></script>\n" +
                    "        <script src=\"../assets/js/plugins.js\"></script>\n" +
                    "        <script src=\"../assets/js/main.js\"></script>\n" +
                    "        <script src=\"../assets/product-details.js\"></script>\n" +
                    "    </body>\n" +
                    "</html>\n" +
                    "\n";
            File newHtmlFile = new File("gamesoftwares/" + softwareGame.getId() + ".html");
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(newHtmlFile));
                bw.write(html);
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static void createLaptopsHtmlFiles(List<Laptop> i_LaptopArray) {
        for (int i = 0; i < i_LaptopArray.size(); i++) {
            Laptop laptop = i_LaptopArray.get(i);
            String osDisplay = getOsDisplay(i_LaptopArray.get(i).getOperation_system());
            String touchScreenDisplay = "No";
            if (laptop.getTouch_screen()) touchScreenDisplay = "Yes";
            String ssdDisplay = "No";
            if (laptop.getStorage().getM_SSD()) ssdDisplay = "Yes";

            String StringImagesFirst = getFirstImagesString(laptop.getImagesUrls());
            String StringImagesSecond = getSecondImagesString(laptop.getImagesUrls());

            String priceString = "$" + String.valueOf ((int) laptop.getPrice());
            if(laptop.getPrice() == 0)
                priceString = "";


            String html = "<!doctype html>\n" +
                    "<html class=\"no-js\" lang=\"zxx\">\n" +
                    "    <head>\n" +
                    "        <meta charset=\"utf-8\">\n" +
                    "        <meta http-equiv=\"x-ua-compatible\" content=\"ie=edge\">\n" +
                    "        <title>LaptoPlus - Laptops - "+laptop.getModel_name()+"</title>\n" +
                    "        <meta name=\"description\" content=\""+laptop.getDescription()+"\">\n" +
                    "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                    "        <!-- Favicon -->\n" +
                    "        <link rel=\"shortcut icon\" type=\"image/x-icon\" href=\"../assets/img/favicon.png\">\n" +
                    "\t\t\n" +
                    "\t\t<!-- all css here -->\n" +
                    "        <link rel=\"stylesheet\" href=\"../assets/css/bootstrap.min.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"../assets/css/animate.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"../assets/css/owl.carousel.min.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"../assets/css/chosen.min.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"../assets/css/easyzoom.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"../assets/css/meanmenu.min.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"../assets/css/themify-icons.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"../assets/css/icofont.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"../assets/css/font-awesome.min.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"../assets/css/bundle.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"../assets/css/style.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"../assets/css/responsive.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"../assets/product-details.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"../assets/footer.css\">\n" +
                    "        <link rel=\"stylesheet\" href=\"../assets/navbar.css\">\n" +
                    "\n" +
                    "        <script src=\"../assets/js/vendor/modernizr-2.8.3.min.js\"></script>\n" +
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
                    "            <div class=\"breadcrumb-area pt-255 pb-170\" style=\"background-image: url(../assets/img/banner/"+laptop.getCompany_name()+".jpg)\">\n" +
                    "                <div class=\"container-fluid\">\n" +
                    "                    <div class=\"breadcrumb-content text-center\">\n" +
                    "                        <h2>Laptop details </h2>\n" +
                    "                        <ul>\n" +
                    "                            <li>\n" +
                    "                                <a href=\"#\">Companies</a>\n" +
                    "                            </li>\n" +
                    "                            <li>\n" +
                    "                                <a href=\"../companies/" + laptop.getCompany_name() + ".html" + "\">" + laptop.getCompany_name() + "</a>\n" +
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
                    "                                    <span>" + priceString + "</span>\n" +
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
                    "        <script src=\"../assets/js/vendor/jquery-1.12.0.min.js\"></script>\n" +
                    "        <script src=\"../assets/js/popper.js\"></script>\n" +
                    "        <script src=\"../assets/js/bootstrap.min.js\"></script>\n" +
                    "        <script src=\"../assets/js/isotope.pkgd.min.js\"></script>\n" +
                    "        <script src=\"../assets/js/imagesloaded.pkgd.min.js\"></script>\n" +
                    "        <script src=\"../assets/js/jquery.counterup.min.js\"></script>\n" +
                    "        <script src=\"../assets/js/waypoints.min.js\"></script>\n" +
                    "        <script src=\"../assets/js/owl.carousel.min.js\"></script>\n" +
                    "        <script src=\"../assets/js/plugins.js\"></script>\n" +
                    "        <script src=\"../assets/js/main.js\"></script>\n" +
                    "        <script src=\"../assets/product-details.js\"></script>\n" +
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

