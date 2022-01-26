package com.androidclass.androidsceneform;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class MainActivity extends AppCompatActivity {

    ArFragment arFragment;
    private ModelRenderable bearRenderable;
    ImageView bear;
    View arrayView[];




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arFragment=(ArFragment)getSupportFragmentManager().findFragmentById(R.id.sceneform_ux_frahment);

        bear=(ImageView)findViewById(R.id.bear);

        setArrrayView();
        setClickListener();
        setupModel();
        arFragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {

                    Anchor anchor=hitResult.createAnchor();
                    AnchorNode anchorNode=new AnchorNode(anchor);
                    anchorNode.setParent(arFragment.getArSceneView().getScene());
                    createModel(anchorNode,selected);
            }
        });
    }

    private void setupModel() {
        ModelRenderable.builder()
                .setSource(this,R.raw.bear)
                .build().thenAccept(renderable->bearRenderable=renderable)
                .exceptionally(
                        throwable ->{
                                Toast.makeText(this,"Unable to load bear model",Toast.LENGTH_SHORT).show();

                        return null;
                        });
//        ModelRenderable.builder()
//                .setSource(context:this,R.raw.bear)
//                .build().thenAccept(renderable->bearRenderable=renderable)
//                .exceptionally(
//                        throwable ->{
//                            Toast.makeText(context: this, text: "Unable to load bear model",Toast.LENGTH_SHORT).show();
//
//                            return null;
//                        });

    }

    private void createModel(AnchorNode anchorNode,int selected)
    {
        if(selected==1)
        {
            TransformableNode bear=new TransformableNode(arFragment.getTransformationSystem());
            bear.setParent(anchorNode);
            bear.setRenderable(bearRenderable);
            bear.select();
            addName(anchorNode,bear,"Bear");

        }
    }
    private void addName(AnchorNode anchorNode,TransformableNode model,String name){

        final ViewRenderable name_animal;
        ViewRenderable.builder().setView(this,R.layout.name_animal);
                       .build()
                       .thenAccept(viewRenderable ->{
                           TransformableNode nameView=new TransformableNode(arFragment.getTransformationSystem());
                           nameView.setLocalPosition(new Vector3(0f,model.getLocalPosition().y+0.5f,0));
                           nameView.setParent(anchorNode);
                           nameView.setRenderable(viewRenderable);
                           nameView.select();

                           TextView txt_name=(TextView)viewRenderable.getView();
                           txt_name.setText(name);

                           txt_name.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View view) {

                               }
                           });
                       });



    }

    private void setClickListener() {
        for(int i=0;i<arrayView.length;i++)
            arrayView[i].setOnClickListener((View.OnClickListener) this);
    }

    private void setArrrayView(){

    }
    public void onClick(View view)
    {
        if(view.getId()==R.id.bear) {
            selected = 1;
            setBackground(view.getId());
        }
    }
    private void setBackground(int id){
        for(int i=0;i<arrayView.length;i++)
        {
            if(arrayView[i].getId()==id)
            {
                arrayView[i].setBackgroundColor(Color.parseColor(colorString:"#80333639"));
            }
            else
            {
                arrayView[i].setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }
}