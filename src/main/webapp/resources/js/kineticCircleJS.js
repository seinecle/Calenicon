
function addSegments(json){
    
    //COLORS
    var MAXSTROKEWEIGHT = 20;
    
    var listColors = new Array();
    createColors();

    
    //SKETCH SIZE
    
    var SKETCH_WIDTHf = 800;
    var SKETCH_HEIGTHf = 800;
    var SKETCH_CENTER_X = SKETCH_WIDTHf / 2;
    var SKETCH_CENTER_Y = SKETCH_HEIGTHf / 2;
    
    
    //SPACE BETWEEN SEGMENTS
    var INTERSTICE_SEGMENT = 0.2;
    
    //SPACE BETWEEN LABELS AND CIRCLE
    var INTERSTICE_LABEL = 7;
    
    // WIDTH OF LABEL (HAS TO BE DETERMINED IN ADVANCE...)
    var widthLabel = 300;
        
    //INTERACTION
    var currClickedAuthor;
    
    
    function  Segment (newKey,newValue, newCount, newYear){
        this.key = newKey;
        this.value = newValue;
        this.count = newCount;
        this.year = newYear;
        function toString(){
            return (this.value);
        }
    }
    
    
    var segments = new Array();
    //    segments.push(new Segment("test coauthor 1",5,false));
    //    segments.push(new Segment("test coauthor 2",3,false));
    //    segments.push(new Segment("test coauthor 3",8,false));
    //    segments.push(new Segment("test coauthor 4",2,false));
    //    segments.push(new Segment("test coauthor 5",1,false));
    //    segments.push(new Segment("coco",1,false));
    //    segments.push(new Segment("test main author",1,true));
    
    var data = $.parseJSON(json);
    if(data) {
        for(i=0; i<data.length; i++) {
            var segment = data[i];
            //                        alertHelloWorld();
            //                    alert("hello world!");
            //                    addSegment(segment.label, segment.count,segment.isMain);
            segments.push(new Segment(segment.key,segment.value,segment.count,segment.year));
        }
    }
    
    

    
    //NUMBER SEGMENTS
    var nbSegments = segments.length - 1;
    console.log("nb Segments: "+nbSegments);
    
    
    //RESIZE SKETCH ACCORDINGLY
    SKETCH_WIDTHf = Math.min(800 + nbSegments*7,1200);
    SKETCH_HEIGTHf = SKETCH_WIDTHf*0.75;
    SKETCH_CENTER_X = SKETCH_WIDTHf / 2;
    SKETCH_CENTER_Y = SKETCH_HEIGTHf / 2;
    

    //REDRAW THE SKETCH AT THESE NEW DIMENSIONS
    var stage = new Kinetic.Stage({
        container: document.getElementById('vizkeywords'),
        width: SKETCH_WIDTHf,
        height: SKETCH_HEIGTHf
    });
    console.log("kinetic stage created");

    

    var layerText = new Kinetic.Layer();
    
    console.log("kinetic layers successfully instantiated");
    
    //TEXT SIZE
    var textSize = 10+ (SKETCH_WIDTHf-600)/100 - nbSegments/20;
    
    
    //CIRCLE SIZE
    
 
    countSegments = 0;
    countBasicUnits = 0;
    

    // DRAWING LABELS FOR OTHER SEGMENTS    
    var labelsArr = [];
 
    for (i = 0; i < nbSegments; i++) {
        
        funcDrawLabel(i);
        if ()
        countSegments++;
    }
    stage.add(layerLabelSegments);
    console.log("all text added");


    function funcDrawLabel(i){



        //COORDINATES OF THE LABEL FOR THE CURRENT SEGMENT
        var xLabelSegment = SKETCH_CENTER_X + ;
        var yLabelSegment = ;
     
        labelsArr[i] = new Kinetic.Text({
            x: xLabelSegment,
            y: yLabelSegment,
            text: segments[i].label,
            align:allo,
            width:widthLabel,
            offset:[0,(textSize/2)],
            fontSize: textSize,
            fontFamily: 'Calibri',
            textFill: 'black',
            rotation: thetaLabel
         
        });


        layerText.add(labelsArr[i]);
    }
    

    function createColors(){
        listColors.push("#E78B27");
        listColors.push("#6130D5");
        listColors.push("#7FE5E5");
        listColors.push("#59E341");
        listColors.push("#2B2342");
        listColors.push("#E03C86");
        listColors.push("#3D5D21");
        listColors.push("#A48BD9");
        listColors.push("#E7B39C");
        listColors.push("#71211B");
        listColors.push("#DDE238");
        listColors.push("#D1E693");
        listColors.push("#E565DE");
        listColors.push("#E7432B");
        listColors.push("#3F837B");
        listColors.push("#50B376");
        listColors.push("#A5953F");
        listColors.push("#4596BF");
        listColors.push("#A1606A");
        listColors.push("#422592");
        listColors.push("#E898C3");
        listColors.push("#90613D");
        listColors.push("#C6D4DE");
        listColors.push("#501C65");
        listColors.push("#9B87A3");
        listColors.push("#233119");
        listColors.push("#5A1B37");
        listColors.push("#9E3397");
        listColors.push("#E1BB39");
        listColors.push("#E47772");
        listColors.push("#BE31ED");
        listColors.push("#78E37D");
        listColors.push("#2F507D");
        listColors.push("#405EB4");
        listColors.push("#86A52E");
        listColors.push("#BDEABF");
        listColors.push("#6159D8");
        listColors.push("#5C99E6");
        listColors.push("#1D3940");
        listColors.push("#E0375F");
        listColors.push("#E57647");
        listColors.push("#4AB238");
        listColors.push("#65E9B6");
        listColors.push("#A0E13A");
        listColors.push("#EBC787");
        listColors.push("#89AA5E");
        listColors.push("#D6BAE3");
        listColors.push("#DE65B5");
        listColors.push("#C28540");
        listColors.push("#8D8457");
        listColors.push("#A4998C");
        listColors.push("#9A46CC");
        listColors.push("#962D70");
        listColors.push("#7C50A9");
        listColors.push("#AF3539");
        listColors.push("#DF31AD");
        listColors.push("#C5DE65");
        listColors.push("#58BAA8");
        listColors.push("#693D65");
        listColors.push("#6B5558");
        listColors.push("#A63B5C");
        listColors.push("#471F16");
        listColors.push("#3F802C");
        listColors.push("#4F4518");
        listColors.push("#E2DDC0");
        listColors.push("#336B48");
        listColors.push("#AE4418");
        listColors.push("#A560A2");
        listColors.push("#6A7EEB");
        listColors.push("#2A2E6A");
        listColors.push("#DA6E97");
        listColors.push("#D79096");
        listColors.push("#595842");
        listColors.push("#DFB8C3");
        listColors.push("#AE7F6E");
        listColors.push("#8FBA8C");
        listColors.push("#97B9B1");
        listColors.push("#6F85BB");
        listColors.push("#2D6A85");
        listColors.push("#D336D7");
        listColors.push("#6FC6E9");
        listColors.push("#DF9CE3");
        listColors.push("#33282A");
        listColors.push("#C779E1");
        listColors.push("#655E9A");
        listColors.push("#7E4840");
        listColors.push("#61A5B3");
        listColors.push("#794911");
        listColors.push("#BDB486");
        listColors.push("#396062");
        listColors.push("#9F6A90");
        listColors.push("#595973");
        listColors.push("#756F22");
        listColors.push("#A75837");
        listColors.push("#D79B69");
        listColors.push("#7B878B");
        listColors.push("#B5841D");
        listColors.push("#A3B0DC");
        listColors.push("#648B69");
        listColors.push("#DCC168");
        listColors.push("#E78B27");
        listColors.push("#6130D5");
        listColors.push("#7FE5E5");
        listColors.push("#59E341");
        listColors.push("#2B2342");
        listColors.push("#E03C86");
        listColors.push("#3D5D21");
        listColors.push("#A48BD9");
        listColors.push("#E7B39C");
        listColors.push("#71211B");
        listColors.push("#DDE238");
        listColors.push("#D1E693");
        listColors.push("#E565DE");
        listColors.push("#E7432B");
        listColors.push("#3F837B");
        listColors.push("#50B376");
        listColors.push("#A5953F");
        listColors.push("#4596BF");
        listColors.push("#A1606A");
        listColors.push("#422592");
        listColors.push("#E898C3");
        listColors.push("#90613D");
        listColors.push("#C6D4DE");
        listColors.push("#501C65");
        listColors.push("#9B87A3");
        listColors.push("#233119");
        listColors.push("#5A1B37");
        listColors.push("#9E3397");
        listColors.push("#E1BB39");
        listColors.push("#E47772");
        listColors.push("#BE31ED");
        listColors.push("#78E37D");
        listColors.push("#2F507D");
        listColors.push("#405EB4");
        listColors.push("#86A52E");
        listColors.push("#BDEABF");
        listColors.push("#6159D8");
        listColors.push("#5C99E6");
        listColors.push("#1D3940");
        listColors.push("#E0375F");
        listColors.push("#E57647");
        listColors.push("#4AB238");
        listColors.push("#65E9B6");
        listColors.push("#A0E13A");
        listColors.push("#EBC787");
        listColors.push("#89AA5E");
        listColors.push("#D6BAE3");
        listColors.push("#DE65B5");
        listColors.push("#C28540");
        listColors.push("#8D8457");
        listColors.push("#A4998C");
        listColors.push("#9A46CC");
        listColors.push("#962D70");
        listColors.push("#7C50A9");
        listColors.push("#AF3539");
        listColors.push("#DF31AD");
        listColors.push("#C5DE65");
        listColors.push("#58BAA8");
        listColors.push("#693D65");
        listColors.push("#6B5558");
        listColors.push("#A63B5C");
        listColors.push("#471F16");
        listColors.push("#3F802C");
        listColors.push("#4F4518");
        listColors.push("#E2DDC0");
        listColors.push("#336B48");
        listColors.push("#AE4418");
        listColors.push("#A560A2");
        listColors.push("#6A7EEB");
        listColors.push("#2A2E6A");
        listColors.push("#DA6E97");
        listColors.push("#D79096");
        listColors.push("#595842");
        listColors.push("#DFB8C3");
        listColors.push("#AE7F6E");
        listColors.push("#8FBA8C");
        listColors.push("#97B9B1");
        listColors.push("#6F85BB");
        listColors.push("#2D6A85");
        listColors.push("#D336D7");
        listColors.push("#6FC6E9");
        listColors.push("#DF9CE3");
        listColors.push("#33282A");
        listColors.push("#C779E1");
        listColors.push("#655E9A");
        listColors.push("#7E4840");
        listColors.push("#61A5B3");
        listColors.push("#794911");
        listColors.push("#BDB486");
        listColors.push("#396062");
        listColors.push("#9F6A90");
        listColors.push("#595973");
        listColors.push("#756F22");
        listColors.push("#A75837");
        listColors.push("#D79B69");
        listColors.push("#7B878B");
        listColors.push("#B5841D");
        listColors.push("#A3B0DC");
        listColors.push("#648B69");
        listColors.push("#DCC168");

    }


};