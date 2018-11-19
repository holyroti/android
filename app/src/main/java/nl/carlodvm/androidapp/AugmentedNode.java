package nl.carlodvm.androidapp;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.ar.core.AugmentedImage;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class AugmentedNode extends AnchorNode {

    private static final String TAG = "AugmentedImageNode";

    private Renderable model;
    private TransformableNode TransformableModel;

    public AugmentedNode(Context context, String path) {
        ModelRenderable
                .builder()
                .setSource(context, Uri.parse(path))
                .build()
                .thenAccept((renderable) -> model = renderable)
                .exceptionally(throwable -> {
                    Log.e(TAG, "Exception loading", throwable);
                    return null;
                });
        ;
    }

    @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
    public void renderNode(AugmentedImage image, ArFragment arFragment) {
        this.setAnchor(image.createAnchor(image.getCenterPose()));
        this.setParent(arFragment.getArSceneView().getScene());

        TransformableNode transfomNode = new TransformableNode(arFragment.getTransformationSystem());
        transfomNode.setLocalScale(new Vector3(0.3f, 0.3f, 0.3f));
        transfomNode.setLocalRotation(Quaternion.axisAngle(new Vector3(1.0f, 0.0f, 0.0f), 90f));
        Vector3 center = new Vector3(.0f, .5f, .0f);
        transfomNode.setWorldPosition(center);
        transfomNode.getScaleController().setEnabled(false);
        transfomNode.getRotationController().setEnabled(false);
        transfomNode.getTranslationController().setEnabled(false);
        transfomNode.setParent(this);
        transfomNode.setRenderable(model);
        transfomNode.select();
    }

    public TransformableNode getTransformableModel() {
        return TransformableModel;
    }
}
