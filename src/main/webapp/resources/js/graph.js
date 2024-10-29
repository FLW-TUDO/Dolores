
/*
function ext() {

$(".jqplot-xaxis-tick").hide();
    $(".jqplot-yaxis-tick").hide();

 this.cfg.axes.xaxis.tickInterval = 1;
    this.cfg.axes.yaxis.show = false;
   
}*/


$( document ).ready(function() {
    console.log( "ready!" );
    
    $(".jqplot-xaxis-tick").hide();
    $(".jqplot-yaxis-tick").hide();
    
    //this.cfg.axes.xaxis.tickInterval = 1;
    //this.cfg.axes.yaxis.show = false;
    
});

/*
function ext2() {

     $(".jqplot-xaxis-tick").hide();
    $(".jqplot-yaxis-tick").hide();
    
    this.cfg.axes.xaxis.tickInterval = 1;
    this.cfg.axes.yaxis.show = false;
    /*
    axesDefaults =  {
        showTicks: false,
        showTickMarks: false       
    };


    this.cfg.axes.yaxis.tickOptions = {
        formatString: '%d'
    };
    this.cfg.axes.xaxis.tickOptions = {
        formatString: '%d'
    };

    this.cfg.axes.yaxis =  {
        ticks: ['']
    };

    
    $(".jqplot-xaxis-tick").hide();
    $(".jqplot-yaxis-tick").hide();*
}*/



function exportChart() {
    //export image
    $('#form\\:output').empty().append(chart.exportAsImage());

    //center dialog with new content
    dlg.initPosition();

    //show the dialog
    dlg.show();
}

