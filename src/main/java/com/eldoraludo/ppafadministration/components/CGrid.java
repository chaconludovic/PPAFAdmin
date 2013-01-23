package com.eldoraludo.ppafadministration.components;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.tapestry5.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.corelib.components.Select;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.internal.InternalConstants;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

import com.eldoraludo.ppafadministration.stream.ExcelStreamResponse;
import com.eldoraludo.ppafadministration.stream.GridToExcelConverter;
import com.eldoraludo.ppafadministration.util.EvenOdd;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@SupportsInformalParameters
@SuppressWarnings("rawtypes")
public class CGrid {

    @Parameter(required = true)
    @Property
    private BeanModel model;

    @Parameter(required = true)
    @Property
    private Object row;

    @Parameter(required = true)
    @Property
    private GridDataSource source;

    @Property
    @Parameter(required = false)
    private String filtre;

    @Parameter(defaultPrefix = BindingConstants.BLOCK, required = false)
    @Property
    private Block emptyCGrid;

    @Parameter(defaultPrefix = BindingConstants.BLOCK, required = false)
    @Property
    private Block filterMore;

    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String idTableau;

    @Property
    @Parameter(required = false, defaultPrefix = BindingConstants.LITERAL)
    private String initialSortedColumn;

    @Parameter(required = false, value = "true")
    private boolean initialSortAscending;

    @Parameter(required = false, cache = false)
    private String rowClass;

    @Parameter(value = "this", allowNull = false)
    @Property(write = false)
    private PropertyOverrides overrides;

    @Property
    @Persist
    private EvenOdd evenOdd;

    @Property
    @Persist
    private int nombreLignesAffichees;

    @Inject
    private Messages messages;

    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;

    @InjectComponent
    private Zone cGridZone;

    @InjectComponent("cGrid")
    @Property
    private Grid cGridComponent;

    @Inject
    private ComponentResources componentResources;

    @Property
    @Persist
    private Map<Integer, String> listePagination;

    @Inject
    private GridToExcelConverter gridToExcelConverter;

    @Property
    private int rowIndex;

    public Zone getGridZone() {
        return cGridZone;
    }

    public Grid getGrid() {
        return cGridComponent;
    }

    public GridDataSource getDataSource() {
        return source;
    }

    @SetupRender
    public void setupRender() {
        initNbLignesAffichees();
        initListePagination();
        initColumnSort();
    }

    public boolean getFiltreIsBound() {
        return componentResources.isBound("filtre");
    }

    private void initColumnSort() {
        if (initialSortedColumn != null && cGridComponent.getSortModel().getSortConstraints().isEmpty()) {
            cGridComponent.getSortModel().updateSort(initialSortedColumn);
            if (!initialSortAscending) {
                cGridComponent.getSortModel().updateSort(initialSortedColumn);
            }
        }
    }

    private void initNbLignesAffichees() {
        if (nombreLignesAffichees == 0) {
            nombreLignesAffichees = 100;
        }
    }

    public void initListePagination() {
        if (listePagination == null) {
            listePagination = new LinkedHashMap<Integer, String>();
            listePagination.put(10, "10");
            listePagination.put(25, "25");
            listePagination.put(50, "50");
            listePagination.put(100, "100");
            listePagination.put(200, "200");
        }
    }

    public Integer getNombreLignesAfficheesRow() {
        return nombreLignesAffichees == -1 ? getNombreLignesTotal() : nombreLignesAffichees;
    }

    public int getNumeroPremiereLigne() {
        return ((cGridComponent.getRowsPerPage() * (cGridComponent.getCurrentPage() - 1)) + 1);
    }

    public int getNumeroDernierePage() {
        return cGridComponent.getRowsPerPage() > 0 ? (int) Math.ceil((double) getNombreLignesTotal()
                / (double) cGridComponent.getRowsPerPage()) : 1;
    }

    public int getNumeroDerniereLigne() {
        int numeroDerniereLigne = (cGridComponent.getRowsPerPage() * cGridComponent.getCurrentPage());
        return numeroDerniereLigne > getNombreLignesTotal() ? getNombreLignesTotal() : numeroDerniereLigne;
    }

    public boolean getSourceGridEstVide() {
        return source.getAvailableRows() == 0;
    }

    public String getSourceGridEstVideClass() {
        return source.getAvailableRows() == 0 ? "hide-content" : "";
    }

    public String getPaginationInfo() {
        return messages.format("component.pagination.info", getNumeroPremiereLigne(), getNumeroDerniereLigne(),
                getNombreLignesTotal());
    }

    public String getRowClass() {
        if (rowIndex == 0) {
            evenOdd = new EvenOdd();
        }
        return evenOdd.getNext() + (this.rowClass == null ? "" : " " + this.rowClass);
    }

    public String getCssBoutonPremierePage() {
        return (cGridComponent.getRowsPerPage() >= getNombreLignesTotal()) || (cGridComponent.getCurrentPage() == 1) ? "disabled"
                : "";
    }

    public String getCSSBoutonDernierePage() {
        return (cGridComponent.getRowsPerPage() >= getNombreLignesTotal())
                || (cGridComponent.getCurrentPage() == getNumeroDernierePage()) ? "disabled" : "";
    }

    public boolean getAfficheBoutonPage() {
        return (!(getNumeroDernierePage() == 1));
    }

    public int getNombreLignesTotal() {
        return source.getAvailableRows();
    }

    @OnEvent("gotoDerniere")
    public void onEventFromGotoDerniere() {
        cGridComponent.setCurrentPage(getNumeroDernierePage());
        ajaxResponseRenderer.addRender("cGridZone", cGridZone);
    }

    @OnEvent("gotoPremiere")
    public void onEventFromGotoPremiere() {
        cGridComponent.setCurrentPage(1);
        ajaxResponseRenderer.addRender("cGridZone", cGridZone);
    }

    @OnEvent(value = Select.CHANGE_EVENT, component = "listePaginationPossible")
    void onChangeFromListePaginationPossible() {
        ajaxResponseRenderer.addRender("cGridZone", cGridZone);
    }

    @OnEvent(value = "keyup", component = "filtre")
    void filtreTableau(@RequestParameter(value = "filtre", allowBlank = true) String newFiltre) {
        this.filtre = newFiltre;
        ajaxResponseRenderer.addRender("cGridZone", cGridZone);
    }

    @OnEvent(component = "cGridPager", value = InternalConstants.GRID_INPLACE_UPDATE)
    public void onEventFromGridPager() {
        ajaxResponseRenderer.addRender("cGridZone", cGridZone);

    }

    @OnEvent("export")
    public StreamResponse onExport() throws IOException {
        HSSFWorkbook workbook = gridToExcelConverter.convertGridToExcelWorkbook(model, source);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        out.close();
        return new ExcelStreamResponse(out, "export");
    }

}