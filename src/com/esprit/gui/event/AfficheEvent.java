/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.gui.event;


import static com.codename1.charts.util.ColorUtil.rgb;
import com.codename1.components.ImageViewer;
import com.codename1.components.ShareButton;
import com.codename1.components.SpanLabel;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import com.esprit.entity.event.event_likes;
import com.esprit.service.event.EventLikeService;
import com.esprit.techevent.Session;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 *
 * @author Dorsaf
 */
public class AfficheEvent {

    Form f;
    Image img1, img2, cal1, fb, comment ,price;

    public AfficheEvent(Resources theme) {

        f = (Form) new Form(new BoxLayout(BoxLayout.Y_AXIS));
        f.getStyle().setBgColor(rgb(255, 255, 255));
        img1 = theme.getImage("like1.png");
      
        img2 = theme.getImage("dislike.png");
       
        Button like = new Button();
        like.setIcon(img1);
        Button dislike = new Button();
        Button facebook = new Button();
        
        dislike.setIcon(img2);
        cal1 = theme.getImage("calendar (1).png");
        fb = theme.getImage("facebook.png");
        facebook.setIcon(fb);
        comment = theme.getImage("chat (4).png");
        price = theme.getImage("money-bag.png");
        Container co = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container start = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container end = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container all = new Container(new BoxLayout(BoxLayout.X_AXIS));
        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(400, 400), true);
        EventLikeService ev = new EventLikeService();
        List<event_likes> list = ev.getEventLike();
        like.setVisible(true);
        dislike.setVisible(false);
        for (event_likes event : list) {
            if ((Session.user.getId() == event.getUser_id()) && (event.getEvent_id() == Session.current_event.getId_event())) {
                System.out.println("liked");
                dislike.setVisible(true);
                like.setVisible(false);
            }
        }
        
        Image i = URLImage.createToStorage(placeholder, "http://localhost/PIDEV/dorsaf/TechEvent/web/img/uploads/" + Session.current_event.getPhoto(), "http://localhost/PIDEV/dorsaf/TechEvent/web/img/uploads/" + Session.current_event.getPhoto(), URLImage.RESIZE_SCALE);
        i.scaledWidth(10);
        ImageViewer img = new ImageViewer(i);
        img.setHeight(70);
        Label name = new Label("Event Name :");
        Label name1 = new Label();
        name1.setText(Session.current_event.getEvent_name());
        name1.getAllStyles().setPaddingLeft(30);
        name1.getAllStyles().isUnderline();
        co.add(name1);
        f.add(co);
        f.add(like);
        f.add(dislike);
   
        f.add(img);
        
        
        
        
        
        
        Label description = new Label("Description :");
        SpanLabel description1 = new SpanLabel();
        f.add(description);
        description1.setText(Session.current_event.getDescription());
        description1.setEnabled(true);
        f.add(description1);
        
        
        Label startDate = new Label();
        startDate.setText("hhhhhhhh");
        start.add(cal1);
        start.add(startDate);
        f.add(start);

        Label endDate = new Label();
        endDate.setText("hhhhhhhh");
        end.add(cal1);
        end.add(endDate);
        f.add(end);
        
        all.add(facebook);
        all.add(comment);
        f.add(all);
        
           event_likes ev1=new event_likes(5, Session.user.getId(), Session.current_event.getId_event());
        like.addActionListener((evt) -> {
            
           ev.ajout(ev1);
            System.out.println("added");
            dislike.setVisible(true);
            like.setVisible(false);
        });
        dislike.addActionListener((evt) -> {
            ev.Delete(ev1);
            System.out.println("deleted");
            dislike.setVisible(false);
            like.setVisible(true);
        });
        facebook.addActionListener((evt) -> {
            
            Form hi = new Form("ShareButton");
        
        ShareButton sb = new ShareButton();
sb.setText("Share Screenshot");
hi.add(sb);

Image screenshot = Image.createImage(hi.getWidth(), hi.getHeight());
hi.revalidate();
hi.setVisible(true);
hi.paintComponent(screenshot.getGraphics(), true);

String imageFile = FileSystemStorage.getInstance().getAppHomePath() + "screenshot.png";
try(OutputStream os = FileSystemStorage.getInstance().openOutputStream(imageFile)) {
    ImageIO.getImageIO().save(screenshot, os, ImageIO.FORMAT_PNG, 1);
} catch(IOException err) {
    Log.e(err);
}
sb.setImageToShare(imageFile, "image/png");
       hi.show(); 
            
            
            
            
            
            
            
            
            
        });
        
        
        
        
        
        
        
        
        
        
        
        
        
        f.show();
        
     

    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

}
