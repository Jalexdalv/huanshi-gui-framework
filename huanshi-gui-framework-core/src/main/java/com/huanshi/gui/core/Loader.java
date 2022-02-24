package com.huanshi.gui.core;

import com.huanshi.gui.application.AbstractApplication;
import com.huanshi.gui.common.annotation.AutowiredData;
import com.huanshi.gui.common.annotation.AutowiredManager;
import com.huanshi.gui.common.annotation.AutowiredModel;
import com.huanshi.gui.common.annotation.AutowiredModelComponent;
import com.huanshi.gui.common.annotation.AutowiredView;
import com.huanshi.gui.common.annotation.AutowiredViewComponent;
import com.huanshi.gui.common.annotation.ModelComponent;
import com.huanshi.gui.common.annotation.ViewComponent;
import com.huanshi.gui.common.annotation.Viewport;
import com.huanshi.gui.common.annotation.ViewportComponent;
import com.huanshi.gui.common.config.Parser;
import com.huanshi.gui.common.data.Key;
import com.huanshi.gui.common.exception.AnnotationTypeException;
import com.huanshi.gui.common.exception.CircularDependencyException;
import com.huanshi.gui.common.exception.DuplicateKeyException;
import com.huanshi.gui.common.exception.IllegalAnnotationParameterException;
import com.huanshi.gui.common.exception.ModelNotFoundException;
import com.huanshi.gui.common.exception.ManagerNotFoundException;
import com.huanshi.gui.common.exception.ViewNotFoundException;
import com.huanshi.gui.common.utils.ReflectUtils;
import com.huanshi.gui.controller.listener.AbstractButtonListener;
import com.huanshi.gui.controller.listener.AbstractComboBoxListener;
import com.huanshi.gui.controller.listener.AbstractIconButtonListener;
import com.huanshi.gui.controller.listener.AbstractTableRowListener;
import com.huanshi.gui.controller.manager.AbstractManager;
import com.huanshi.gui.controller.listener.Listener;
import com.huanshi.gui.model.AbstractModel;
import com.huanshi.gui.model.container.panel.*;
import com.huanshi.gui.model.container.scrollpane.TableScrollPaneModel;
import com.huanshi.gui.model.container.scrollpane.TextAreaScrollPaneModel;
import com.huanshi.gui.model.widget.ButtonModel;
import com.huanshi.gui.model.widget.CheckBoxModel;
import com.huanshi.gui.model.widget.ComboBoxModel;
import com.huanshi.gui.model.widget.IconModel;
import com.huanshi.gui.model.widget.PasswordFieldModel;
import com.huanshi.gui.model.widget.TableModel;
import com.huanshi.gui.model.widget.TextAreaModel;
import com.huanshi.gui.model.widget.TextFieldModel;
import com.huanshi.gui.model.widget.TextModel;
import com.huanshi.gui.view.LayerSwitcher;
import com.huanshi.gui.view.container.Container;
import com.huanshi.gui.view.container.panel.*;
import com.huanshi.gui.view.container.scrollpane.AbstractScrollPane;
import com.huanshi.gui.view.container.scrollpane.TableScrollPane;
import com.huanshi.gui.view.container.scrollpane.TextAreaScrollPane;
import com.huanshi.gui.view.widget.Button;
import com.huanshi.gui.view.widget.CheckBox;
import com.huanshi.gui.view.widget.ComboBox;
import com.huanshi.gui.view.widget.Icon;
import com.huanshi.gui.view.widget.PasswordField;
import com.huanshi.gui.view.widget.Table;
import com.huanshi.gui.view.widget.Text;
import com.huanshi.gui.view.widget.TextArea;
import com.huanshi.gui.view.widget.TextField;
import com.huanshi.gui.view.widget.Widget;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.SwingUtilities;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("all")
public class Loader {
    private static final LinkedList<Class<?>> MODEL_COMPONENT_LIST = new LinkedList<>() {{
        add(IconButtonModel.class);
        add(IconPanelModel.class);
        add(IconTitleModel.class);
        add(IconTextFieldModel.class);
        add(IconPasswordFieldModel.class);
        add(DateTextFieldModel.class);
        add(DialogTitleBarModel.class);
        add(FrameTitleBarModel.class);
        add(LoadingPanelModel.class);
        add(TitleComboBoxModel.class);
        add(TitleDateTextFieldModel.class);
        add(TitleTextAreaScrollPaneModel.class);
        add(TitleTextFieldModel.class);
        add(TitlePasswordFieldModel.class);
        add(TableScrollPaneModel.class);
        add(TextAreaScrollPaneModel.class);
        add(ButtonModel.class);
        add(CheckBoxModel.class);
        add(ComboBoxModel.class);
        add(IconModel.class);
        add(TableModel.class);
        add(TextAreaModel.class);
        add(TextFieldModel.class);
        add(PasswordFieldModel.class);
        add(TextModel.class);
    }};
    private static final HashMap<Key, AbstractModel> LOADED_MODEL_MAP = new HashMap<>();
    private static final LinkedList<Class<?>> VIEW_COMPONENT_LIST = new LinkedList<>() {{
        add(IconButton.class);
        add(IconPanel.class);
        add(IconTitle.class);
        add(IconTextField.class);
        add(IconPasswordField.class);
        add(DateTextField.class);
        add(DialogTitleBar.class);
        add(FrameTitleBar.class);
        add(LoadingPanel.class);
        add(TitleComboBox.class);
        add(TitleDateTextField.class);
        add(TitleTextAreaScrollPane.class);
        add(TitleTextField.class);
        add(TitlePasswordField.class);
        add(TableScrollPane.class);
        add(TextAreaScrollPane.class);
        add(Button.class);
        add(CheckBox.class);
        add(ComboBox.class);
        add(Icon.class);
        add(Table.class);
        add(TextArea.class);
        add(TextField.class);
        add(PasswordField.class);
        add(Text.class);
    }};
    private static final HashMap<Key, Widget> LOADED_VIEW_MAP = new HashMap<>();
    private static final HashMap<Key, LinkedList<Key>> AUTOWIRED_VIEW_MAP = new HashMap<>();
    private static final HashMap<Key, AbstractManager> LOADED_MANAGER_MAP = new HashMap<>();
    private static final HashMap<Key, LinkedList<Key>> AUTOWIRED_MANAGER_MAP = new HashMap<>();
    private static final HashMap<Key, Listener> LOADED_LISTENER_MAP = new HashMap<>();
    private static final HashMap<Key, Object> LOADED_DATA_MAP = new HashMap<>();

    @SneakyThrows
    public static void loadModel(@NotNull String name, @NotNull String config, @NotNull Class<?> clazz) {
        Key key = new Key(name);
        if (LOADED_MODEL_MAP.containsKey(key)) {
            throw new DuplicateKeyException(key);
        }
        AbstractModel model = (AbstractModel) clazz.getConstructor().newInstance();
        Parser parser = new Parser("config/" + config);
        LinkedList<Field> modelComponentList = new LinkedList<>();
        LinkedList<Field> autowiredModelComponentList = new LinkedList<>();
        for (Field field : ReflectUtils.getFieldList(clazz)) {
            field.setAccessible(true);
            ModelComponent modelComponent = field.getAnnotation(ModelComponent.class);
            AutowiredModelComponent autowiredModelComponent = field.getAnnotation(AutowiredModelComponent.class);
            if (ReflectUtils.hasConflictAnnotation(modelComponent, autowiredModelComponent)) {
                throw new AnnotationTypeException(field, modelComponent, autowiredModelComponent);
            }
            if (modelComponent != null) {
                modelComponentList.add(field);
            } else if (autowiredModelComponent != null) {
                autowiredModelComponentList.add(field);
            }
        }
        for (Field field : modelComponentList) {
            ModelComponent modelComponent = field.getAnnotation(ModelComponent.class);
            if (!MODEL_COMPONENT_LIST.contains(field.getType())) {
                throw new AnnotationTypeException(modelComponent, field);
            }
            key.addLast(modelComponent.names());
            if (LOADED_MODEL_MAP.containsKey(key)) {
                throw new DuplicateKeyException(key);
            }
            field.set(model, loadModelComponent(parser, key, field.getType()));
            key.removeLast();
        }
        for (Field field : autowiredModelComponentList) {
            AutowiredModelComponent autowiredModelComponent = field.getAnnotation(AutowiredModelComponent.class);
            if (!MODEL_COMPONENT_LIST.contains(field.getType())) {
                throw new AnnotationTypeException(autowiredModelComponent, field);
            }
            if (autowiredModelComponent.names().length == 0) {
                throw new IllegalAnnotationParameterException(autowiredModelComponent);
            }
            key.addLast(autowiredModelComponent.names());
            if (!LOADED_MODEL_MAP.containsKey(key)) {
                throw new ModelNotFoundException(key);
            }
            if (field.getType() != LOADED_MODEL_MAP.get(key).getClass()) {
                throw new DuplicateKeyException(key);
            }
            field.set(model, LOADED_MODEL_MAP.get(key));
            key.removeLast();
        }
        model.superInit(parser, key);
        LOADED_MODEL_MAP.put(model.getKey(), model);
    }

    @SneakyThrows
    @NotNull
    public static AbstractModel loadModelComponent(@NotNull Parser parser, @NotNull Key key, @NotNull Class<?> clazz) {
        AbstractModel model = (AbstractModel) clazz.getConstructor().newInstance();
        LinkedList<Field> modelComponentList = new LinkedList<>();
        LinkedList<Field> autowiredModelComponentList = new LinkedList<>();
        for (Field field : ReflectUtils.getFieldList(clazz)) {
            field.setAccessible(true);
            ModelComponent modelComponent = field.getAnnotation(ModelComponent.class);
            AutowiredModelComponent autowiredModelComponent = field.getAnnotation(AutowiredModelComponent.class);
            if (ReflectUtils.hasConflictAnnotation(modelComponent, autowiredModelComponent)) {
                throw new AnnotationTypeException(field, modelComponent, autowiredModelComponent);
            }
            if (modelComponent != null) {
                modelComponentList.add(field);
            } else if (autowiredModelComponent != null) {
                autowiredModelComponentList.add(field);
            }
        }
        for (Field field : modelComponentList) {
            ModelComponent modelComponent = field.getAnnotation(ModelComponent.class);
            if (!MODEL_COMPONENT_LIST.contains(field.getType())) {
                throw new AnnotationTypeException(modelComponent, field);
            }
            key.addLast(modelComponent.names());
            if (LOADED_MODEL_MAP.containsKey(key)) {
                throw new DuplicateKeyException(key);
            }
            field.set(model, loadModelComponent(parser, key, field.getType()));
            key.removeLast();
        }
        for (Field field : autowiredModelComponentList) {
            AutowiredModelComponent autowiredModelComponent = field.getAnnotation(AutowiredModelComponent.class);
            if (autowiredModelComponent.names().length == 0) {
                throw new IllegalAnnotationParameterException(autowiredModelComponent);
            }
            if (!MODEL_COMPONENT_LIST.contains(field.getType())) {
                throw new AnnotationTypeException(autowiredModelComponent, field);
            }
            key.addLast(autowiredModelComponent.names());
            if (!LOADED_MODEL_MAP.containsKey(key)) {
                throw new ModelNotFoundException(key);
            }
            if (field.getType() != LOADED_MODEL_MAP.get(key).getClass()) {
                throw new DuplicateKeyException(key);
            }
            field.set(model, LOADED_MODEL_MAP.get(key));
            key.removeLast();
        }
        model.superInit(parser, key);
        LOADED_MODEL_MAP.put(model.getKey(), model);
        return model;
    }

    @SneakyThrows
    @NotNull
    public static Container loadView(@NotNull String name, @NotNull Class<?> clazz) {
        Key key = new Key(name);
        if (LOADED_VIEW_MAP.containsKey(key)) {
            if (clazz != LOADED_VIEW_MAP.get(key).getClass()) {
                throw new DuplicateKeyException(key);
            }
            return (Container) LOADED_VIEW_MAP.get(key);
        }
        if (!LOADED_MODEL_MAP.containsKey(key)) {
            throw new ModelNotFoundException(key);
        }
        Container view = (Container) clazz.getConstructor().newInstance();
        LinkedList<Field> autowiredViewList = new LinkedList<>();
        LinkedList<Field> viewportList = new LinkedList<>();
        LinkedList<Field> viewComponentList = new LinkedList<>();
        LinkedList<Field> viewportComponentList = new LinkedList<>();
        LinkedList<Field> autowiredViewComponentList = new LinkedList<>();
        for (Field field : ReflectUtils.getFieldList(clazz)) {
            field.setAccessible(true);
            AutowiredView autowiredView = field.getAnnotation(AutowiredView.class);
            Viewport viewport = field.getAnnotation(Viewport.class);
            ViewComponent viewComponent = field.getAnnotation(ViewComponent.class);
            ViewportComponent viewportComponent = field.getAnnotation(ViewportComponent.class);
            AutowiredViewComponent autowiredViewComponent = field.getAnnotation(AutowiredViewComponent.class);
            if (ReflectUtils.hasConflictAnnotation(autowiredView, viewport, viewComponent, viewportComponent, autowiredViewComponent)) {
                throw new AnnotationTypeException(field, autowiredView, viewport, viewComponent, viewportComponent, autowiredViewComponent);
            }
            if (autowiredView != null) {
                autowiredViewList.add(field);
            } else if (viewport != null) {
                viewportList.add(field);
            } else if (viewComponent != null) {
                viewComponentList.add(field);
            } else if (viewportComponent != null) {
                viewportComponentList.add(field);
            } else if (autowiredViewComponent != null) {
                autowiredViewComponentList.add(field);
            }
        }
        for (Field field : autowiredViewList) {
            AutowiredView autowiredView = field.getAnnotation(AutowiredView.class);
            if (VIEW_COMPONENT_LIST.contains(field.getType()) || (!AbstractPanel.class.isAssignableFrom(field.getType()) && !AbstractScrollPane.class.isAssignableFrom(field.getType()))) {
                throw new AnnotationTypeException(autowiredView, field);
            }
            Key autowiredViewKey = new Key(autowiredView.names());
            if (AUTOWIRED_VIEW_MAP.containsKey(autowiredViewKey) && AUTOWIRED_VIEW_MAP.get(autowiredViewKey).contains(key)) {
                throw new CircularDependencyException(key, autowiredViewKey);
            }
            if (LOADED_VIEW_MAP.containsKey(autowiredViewKey)) {
                if (field.getType() != LOADED_VIEW_MAP.get(autowiredViewKey).getClass()) {
                    throw new DuplicateKeyException(autowiredViewKey);
                }
                field.set(view, LOADED_VIEW_MAP.get(autowiredViewKey));
                view.addWidget(LOADED_VIEW_MAP.get(autowiredViewKey));
            } else {
                field.set(view, loadView(autowiredViewKey.getValue(), field.getType()));
                view.addWidget((Container) field.get(view));
            }
            if (AUTOWIRED_VIEW_MAP.containsKey(key)) {
                AUTOWIRED_VIEW_MAP.get(key).add(autowiredViewKey);
            } else {
                AUTOWIRED_VIEW_MAP.put(key, new LinkedList<>() {{ add(autowiredViewKey); }});
            }
        }
        for (Field field : viewportList) {
            Viewport viewport = field.getAnnotation(Viewport.class);
            if (!AbstractScrollPane.class.isAssignableFrom(clazz) || VIEW_COMPONENT_LIST.contains(field.getType()) || !AbstractPanel.class.isAssignableFrom(field.getType())) {
                throw new AnnotationTypeException(viewport, field);
            }
            Key viewportKey = new Key(viewport.name());
            if (AUTOWIRED_VIEW_MAP.containsKey(viewportKey) && AUTOWIRED_VIEW_MAP.get(viewportKey).contains(key)) {
                throw new CircularDependencyException(key, viewportKey);
            }
            if (LOADED_VIEW_MAP.containsKey(viewportKey)) {
                if (field.getType() != LOADED_VIEW_MAP.get(viewportKey).getClass()) {
                    throw new DuplicateKeyException(viewportKey);
                }
                field.set(view, LOADED_VIEW_MAP.get(viewportKey));
                view.addWidget(LOADED_VIEW_MAP.get(viewportKey));
            } else {
                field.set(view, loadView(viewportKey.getValue(), field.getType()));
                view.addWidget((Container) field.get(view));
            }
            if (AUTOWIRED_VIEW_MAP.containsKey(key)) {
                AUTOWIRED_VIEW_MAP.get(key).add(viewportKey);
            } else {
                AUTOWIRED_VIEW_MAP.put(key, new LinkedList<>() {{ add(viewportKey); }});
            }
            ((AbstractScrollPane) view).initViewport((Widget) field.get(view));
        }
        for (Field field : viewComponentList) {
            ViewComponent viewComponent = field.getAnnotation(ViewComponent.class);
            if (!VIEW_COMPONENT_LIST.contains(field.getType())) {
                throw new AnnotationTypeException(viewComponent, field);
            }
            key.addLast(viewComponent.names());
            if (LOADED_VIEW_MAP.containsKey(key)) {
                throw new DuplicateKeyException(key);
            }
            field.set(view, loadViewComponent(key, field.getType()));
            view.addWidget((Widget) field.get(view));
            key.removeLast();
        }
        for (Field field : viewportComponentList) {
            ViewportComponent viewportComponent = field.getAnnotation(ViewportComponent.class);
            if (!AbstractScrollPane.class.isAssignableFrom(clazz) || !VIEW_COMPONENT_LIST.contains(field.getType())) {
                throw new AnnotationTypeException(viewportComponent, field);
            }
            key.addLast(viewportComponent.names());
            if (LOADED_VIEW_MAP.containsKey(key)) {
                throw new DuplicateKeyException(key);
            }
            field.set(view, loadViewComponent(key, field.getType()));
            view.addWidget((Widget) field.get(view));
            key.removeLast();
            ((AbstractScrollPane) view).initViewport((Widget) field.get(view));
        }
        for (Field field : autowiredViewComponentList) {
            AutowiredViewComponent autowiredViewComponent = field.getAnnotation(AutowiredViewComponent.class);
            if (autowiredViewComponent.names().length == 0) {
                throw new IllegalAnnotationParameterException(autowiredViewComponent);
            }
            if (!VIEW_COMPONENT_LIST.contains(field.getType())) {
                throw new AnnotationTypeException(autowiredViewComponent, field);
            }
            key.addLast(autowiredViewComponent.names());
            if (!LOADED_VIEW_MAP.containsKey(key)) {
                throw new ViewNotFoundException(key);
            }
            if (field.getType() != LOADED_VIEW_MAP.get(key).getClass()) {
                throw new DuplicateKeyException(key);
            }
            field.set(view, LOADED_VIEW_MAP.get(key));
            view.addWidget(LOADED_VIEW_MAP.get(key));
            key.removeLast();
        }
        view.superInit(LOADED_MODEL_MAP.get(key));
        view.superUpdateWidgetSize();
        view.superUpdateWidgetPosition();
        view.superUpdateContainerSize();
        view.superUpdateContainerPosition();
        LOADED_VIEW_MAP.put(view.getKey(), view);
        Field layerSwitcherLoadedViewMap = LayerSwitcher.class.getDeclaredField("LOADED_VIEW_MAP");
        layerSwitcherLoadedViewMap.setAccessible(true);
        ((HashMap<Key, Widget>) layerSwitcherLoadedViewMap.get(null)).put(view.getKey(), view);
        return view;
    }

    @SneakyThrows
    @NotNull
    public static Widget loadViewComponent(@NotNull Key key, @NotNull Class<?> clazz) {
        if (!LOADED_MODEL_MAP.containsKey(key)) {
            throw new ModelNotFoundException(key);
        }
        Widget view = (Widget) clazz.getConstructor().newInstance();
        if (Container.class.isAssignableFrom(clazz)) {
            LinkedList<Field> viewComponentList = new LinkedList<>();
            LinkedList<Field> viewportComponentList = new LinkedList<>();
            LinkedList<Field> autowiredViewComponentList = new LinkedList<>();
            for (Field field : ReflectUtils.getFieldList(clazz)) {
                field.setAccessible(true);
                ViewComponent viewComponent = field.getAnnotation(ViewComponent.class);
                ViewportComponent viewportComponent = field.getAnnotation(ViewportComponent.class);
                AutowiredViewComponent autowiredViewComponent = field.getAnnotation(AutowiredViewComponent.class);
                if (ReflectUtils.hasConflictAnnotation(viewComponent, viewportComponent, autowiredViewComponent)) {
                    throw new AnnotationTypeException(field, viewComponent, viewportComponent, autowiredViewComponent);
                }
                if (viewComponent != null) {
                    viewComponentList.add(field);
                } else if (viewportComponent != null) {
                    viewportComponentList.add(field);
                } else if (autowiredViewComponent != null) {
                    autowiredViewComponentList.add(field);
                }
            }
            for (Field field : viewComponentList) {
                ViewComponent viewComponent = field.getAnnotation(ViewComponent.class);
                if (!VIEW_COMPONENT_LIST.contains(field.getType())) {
                    throw new AnnotationTypeException(viewComponent, field);
                }
                key.addLast(viewComponent.names());
                if (LOADED_VIEW_MAP.containsKey(key)) {
                    throw new DuplicateKeyException(key);
                }
                field.set(view, loadViewComponent(key, field.getType()));
                ((Container) view).addWidget((Widget) field.get(view));
                key.removeLast();
            }
            for (Field field : viewportComponentList) {
                ViewportComponent viewportComponent = field.getAnnotation(ViewportComponent.class);
                if (!AbstractScrollPane.class.isAssignableFrom(clazz) || !VIEW_COMPONENT_LIST.contains(field.getType())) {
                    throw new AnnotationTypeException(viewportComponent, field);
                }
                key.addLast(viewportComponent.names());
                if (LOADED_VIEW_MAP.containsKey(key)) {
                    throw new DuplicateKeyException(key);
                }
                field.set(view, loadViewComponent(key, field.getType()));
                ((Container) view).addWidget((Widget) field.get(view));
                key.removeLast();
                ((AbstractScrollPane) view).initViewport((Widget) field.get(view));
            }
            for (Field field : autowiredViewComponentList) {
                AutowiredViewComponent autowiredViewComponent = field.getAnnotation(AutowiredViewComponent.class);
                if (autowiredViewComponent.names().length == 0) {
                    throw new IllegalAnnotationParameterException(autowiredViewComponent);
                }
                if (!VIEW_COMPONENT_LIST.contains(field.getType())) {
                    throw new AnnotationTypeException(autowiredViewComponent, field);
                }
                key.addLast(autowiredViewComponent.names());
                if (!LOADED_VIEW_MAP.containsKey(key)) {
                    throw new ViewNotFoundException(key);
                }
                if (field.getType() != LOADED_VIEW_MAP.get(key).getClass()) {
                    throw new DuplicateKeyException(key);
                }
                field.set(view, LOADED_VIEW_MAP.get(key));
                ((Container) view).addWidget(LOADED_VIEW_MAP.get(key));
                key.removeLast();
            }
        }
        view.superInit(LOADED_MODEL_MAP.get(key));
        view.superUpdateWidgetSize();
        view.superUpdateWidgetPosition();
        if (Container.class.isAssignableFrom(clazz)) {
            ((Container) view).superUpdateContainerSize();
            ((Container) view).superUpdateContainerPosition();
        }
        LOADED_VIEW_MAP.put(view.getKey(), view);
        Field layerSwitcherLoadedViewMap = LayerSwitcher.class.getDeclaredField("LOADED_VIEW_MAP");
        layerSwitcherLoadedViewMap.setAccessible(true);
        ((HashMap<Key, Widget>) layerSwitcherLoadedViewMap.get(null)).put(view.getKey(), view);
        return view;
    }

    @SneakyThrows
    public static AbstractManager loadManager(@NotNull String name, @NotNull Class<?> clazz) {
        Key key = new Key(name);
        if (LOADED_MANAGER_MAP.containsKey(key)) {
            if (clazz != LOADED_MANAGER_MAP.get(key).getClass()) {
                throw new DuplicateKeyException(key);
            }
            return LOADED_MANAGER_MAP.get(key);
        }
        AbstractManager manager = (AbstractManager) clazz.getConstructor().newInstance();
        LinkedList<Field> autowiredModelList = new LinkedList<>();
        LinkedList<Field> autowiredManagerList = new LinkedList<>();
        LinkedList<Field> autowiredDataList = new LinkedList<>();
        for (Field field : ReflectUtils.getFieldList(clazz)) {
            field.setAccessible(true);
            AutowiredModel autowiredModel = field.getAnnotation(AutowiredModel.class);
            AutowiredManager autowiredManager = field.getAnnotation(AutowiredManager.class);
            AutowiredData autowiredData = field.getAnnotation(AutowiredData.class);
            if (ReflectUtils.hasConflictAnnotation(autowiredModel, autowiredManager, autowiredData)) {
                throw new AnnotationTypeException(field, autowiredModel, autowiredManager, autowiredData);
            }
            if (autowiredModel != null) {
                autowiredModelList.add(field);
            } else if (autowiredManager != null) {
                autowiredManagerList.add(field);
            } else if (autowiredData != null) {
                autowiredDataList.add(field);
            }
        }
        for (Field field : autowiredModelList) {
            AutowiredModel autowiredModel = field.getAnnotation(AutowiredModel.class);
            Key autowiredModelKey = new Key(autowiredModel.names());
            if (!LOADED_MODEL_MAP.containsKey(autowiredModelKey)) {
                throw new ModelNotFoundException(autowiredModelKey);
            }
            if (field.getType() != LOADED_MODEL_MAP.get(autowiredModelKey).getClass()) {
                throw new DuplicateKeyException(autowiredModelKey);
            }
            field.set(manager, LOADED_MODEL_MAP.get(autowiredModelKey));
        }
        for (Field field : autowiredManagerList) {
            AutowiredManager autowiredManager = field.getAnnotation(AutowiredManager.class);
            Key autowiredManagerKey = new Key(autowiredManager.name());
            if (AUTOWIRED_MANAGER_MAP.containsKey(autowiredManagerKey) && AUTOWIRED_MANAGER_MAP.get(autowiredManagerKey).contains(key)) {
                throw new CircularDependencyException(key, autowiredManagerKey);
            }
            if (LOADED_MANAGER_MAP.containsKey(autowiredManagerKey)) {
                if (field.getType() != LOADED_MANAGER_MAP.get(autowiredManagerKey).getClass()) {
                    throw new DuplicateKeyException(autowiredManagerKey);
                }
                field.set(manager, LOADED_MANAGER_MAP.get(autowiredManagerKey));
            } else {
                field.set(manager, loadManager(autowiredManagerKey.getValue(), field.getType()));
            }
            if (AUTOWIRED_MANAGER_MAP.containsKey(key)) {
                AUTOWIRED_MANAGER_MAP.get(key).add(autowiredManagerKey);
            } else {
                AUTOWIRED_MANAGER_MAP.put(key, new LinkedList<>() {{ add(autowiredManagerKey); }});
            }
        }
        for (Field field : autowiredDataList) {
            AutowiredData autowiredData = field.getAnnotation(AutowiredData.class);
            Key autowiredDataKey = new Key(autowiredData.name());
            if (LOADED_DATA_MAP.containsKey(autowiredDataKey)) {
                if (field.getType() != LOADED_DATA_MAP.get(autowiredDataKey).getClass()) {
                    throw new DuplicateKeyException(autowiredDataKey);
                }
                field.set(manager, LOADED_DATA_MAP.get(autowiredDataKey));
            } else {
                field.set(manager, field.getType().getConstructor().newInstance());
                LOADED_DATA_MAP.put(autowiredDataKey, field.get(manager));
            }
        }
        manager.init();
        LOADED_MANAGER_MAP.put(key, manager);
        return manager;
    }

    @SneakyThrows
    public static void loadListener(@NotNull String[] names, @NotNull Class<?> clazz) {
        Key key = new Key(names);
        if (LOADED_LISTENER_MAP.containsKey(key)) {
            throw new DuplicateKeyException(key);
        }
        Listener listener = (Listener) clazz.getConstructor().newInstance();
        LinkedList<Field> autowiredModelList = new LinkedList<>();
        LinkedList<Field> autowiredModelComponentList = new LinkedList<>();
        LinkedList<Field> autowiredManagerList = new LinkedList<>();
        LinkedList<Field> autowiredDataList = new LinkedList<>();
        for (Field field : ReflectUtils.getFieldList(clazz)) {
            field.setAccessible(true);
            AutowiredModel autowiredModel = field.getAnnotation(AutowiredModel.class);
            AutowiredModelComponent autowiredModelComponent = field.getAnnotation(AutowiredModelComponent.class);
            AutowiredManager autowiredManager = field.getAnnotation(AutowiredManager.class);
            AutowiredData autowiredData = field.getAnnotation(AutowiredData.class);
            if (ReflectUtils.hasConflictAnnotation(autowiredModel, autowiredModelComponent, autowiredManager, autowiredData)) {
                throw new AnnotationTypeException(field, autowiredModel, autowiredModelComponent, autowiredManager, autowiredData);
            }
            if (autowiredModel != null) {
                autowiredModelList.add(field);
            } else if (autowiredModelComponent != null) {
                autowiredModelComponentList.add(field);
            } else if (autowiredManager != null) {
                autowiredManagerList.add(field);
            } else if (autowiredData != null) {
                autowiredDataList.add(field);
            }
        }
        for (Field field : autowiredModelList) {
            AutowiredModel autowiredModel = field.getAnnotation(AutowiredModel.class);
            Key autowiredModelKey = new Key(autowiredModel.names());
            if (!LOADED_MODEL_MAP.containsKey(autowiredModelKey)) {
                throw new ModelNotFoundException(autowiredModelKey);
            }
            if (field.getType() != LOADED_MODEL_MAP.get(autowiredModelKey).getClass()) {
                throw new DuplicateKeyException(autowiredModelKey);
            }
            field.set(listener, LOADED_MODEL_MAP.get(autowiredModelKey));
        }
        for (Field field : autowiredModelComponentList) {
            AutowiredModelComponent autowiredModelComponent = field.getAnnotation(AutowiredModelComponent.class);
            if (autowiredModelComponent.names().length > 0) {
                key.addLast(autowiredModelComponent.names());
            }
            if (!LOADED_MODEL_MAP.containsKey(key)) {
                throw new ModelNotFoundException(key);
            }
            if (field.getType() != LOADED_MODEL_MAP.get(key).getClass()) {
                throw new DuplicateKeyException(key);
            }
            field.set(listener, LOADED_MODEL_MAP.get(key));
            if (autowiredModelComponent.names().length > 0) {
                key.removeLast();
            }
        }
        for (Field field : autowiredManagerList) {
            AutowiredManager autowiredManager = field.getAnnotation(AutowiredManager.class);
            Key autowiredManagerKey = new Key(autowiredManager.name());
            if (!LOADED_MANAGER_MAP.containsKey(autowiredManagerKey)) {
                throw new ManagerNotFoundException(autowiredManagerKey);
            }
            if (field.getType() != LOADED_MANAGER_MAP.get(autowiredManagerKey).getClass()) {
                throw new DuplicateKeyException(autowiredManagerKey);
            }
            field.set(listener, LOADED_MANAGER_MAP.get(autowiredManagerKey));
        }
        for (Field field : autowiredDataList) {
            AutowiredData autowiredData = field.getAnnotation(AutowiredData.class);
            Key autowiredDataKey = new Key(autowiredData.name());
            if (LOADED_DATA_MAP.containsKey(autowiredDataKey)) {
                if (field.getType() != LOADED_DATA_MAP.get(autowiredDataKey).getClass()) {
                    throw new DuplicateKeyException(autowiredDataKey);
                }
                field.set(listener, LOADED_DATA_MAP.get(autowiredDataKey));
            } else {
                field.set(listener, field.getType().getConstructor().newInstance());
                LOADED_DATA_MAP.put(autowiredDataKey, field.get(listener));
            }
        }
        listener.init();
        if (AbstractButtonListener.class.isAssignableFrom(clazz) && LOADED_VIEW_MAP.get(key) instanceof Button button) {
            button.addMouseListener((MouseAdapter) listener);
        } else if (AbstractIconButtonListener.class.isAssignableFrom(clazz) && LOADED_VIEW_MAP.get(key) instanceof IconButton iconButton) {
            iconButton.addMouseListener((MouseAdapter) listener);
        } else if (AbstractComboBoxListener.class.isAssignableFrom(clazz) && LOADED_VIEW_MAP.get(key) instanceof ComboBox comboBox) {
            comboBox.addItemListener((ItemListener) listener);
        } else if (AbstractTableRowListener.class.isAssignableFrom(clazz) && LOADED_VIEW_MAP.get(key) instanceof Table table) {
            table.addMouseListener((MouseAdapter) listener);
        }
        LOADED_LISTENER_MAP.put(key, listener);
    }

    @SneakyThrows
    public static void loadApplication(@NotNull Class<?> clazz) {
        AbstractApplication application = (AbstractApplication) clazz.getConstructor().newInstance();
        LinkedList<Field> autowiredModelList = new LinkedList<>();
        LinkedList<Field> autowiredManagerList = new LinkedList<>();
        LinkedList<Field> autowiredDataList = new LinkedList<>();
        for (Field field : ReflectUtils.getFieldList(clazz)) {
            field.setAccessible(true);
            AutowiredModel autowiredModel = field.getAnnotation(AutowiredModel.class);
            AutowiredManager autowiredManager = field.getAnnotation(AutowiredManager.class);
            AutowiredData autowiredData = field.getAnnotation(AutowiredData.class);
            if (ReflectUtils.hasConflictAnnotation(autowiredModel, autowiredManager, autowiredData)) {
                throw new AnnotationTypeException(field, autowiredModel, autowiredManager, autowiredData);
            }
            if (autowiredModel != null) {
                autowiredModelList.add(field);
            } else if (autowiredManager != null) {
                autowiredManagerList.add(field);
            } else if (autowiredData != null) {
                autowiredDataList.add(field);
            }
        }
        for (Field field : autowiredModelList) {
            AutowiredModel autowiredModel = field.getAnnotation(AutowiredModel.class);
            Key key = new Key(autowiredModel.names());
            if (!LOADED_MODEL_MAP.containsKey(key)) {
                throw new ModelNotFoundException(key);
            }
            if (field.getType() != LOADED_MODEL_MAP.get(key).getClass()) {
                throw new DuplicateKeyException(key);
            }
            field.set(application, LOADED_MODEL_MAP.get(key));
        }
        for (Field field : autowiredManagerList) {
            AutowiredManager autowiredManager = field.getAnnotation(AutowiredManager.class);
            Key autowiredManagerKey = new Key(autowiredManager.name());
            if (!LOADED_MANAGER_MAP.containsKey(autowiredManagerKey)) {
                throw new ManagerNotFoundException(autowiredManagerKey);
            }
            if (field.getType() != LOADED_MANAGER_MAP.get(autowiredManagerKey).getClass()) {
                throw new DuplicateKeyException(autowiredManagerKey);
            }
            field.set(application, LOADED_MANAGER_MAP.get(autowiredManagerKey));
        }
        for (Field field : autowiredDataList) {
            AutowiredData autowiredData = field.getAnnotation(AutowiredData.class);
            Key autowiredDataKey = new Key(autowiredData.name());
            if (LOADED_DATA_MAP.containsKey(autowiredDataKey)) {
                if (field.getType() != LOADED_DATA_MAP.get(autowiredDataKey).getClass()) {
                    throw new DuplicateKeyException(autowiredDataKey);
                }
                field.set(application, LOADED_DATA_MAP.get(autowiredDataKey));
            } else {
                field.set(application, field.getType().getConstructor().newInstance());
                LOADED_DATA_MAP.put(autowiredDataKey, field.get(application));
            }
        }
        SwingUtilities.invokeAndWait(() -> {
            Starter.hideSplash();
        });
        application.run();
    }
}