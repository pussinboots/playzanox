angular.module('HighchartJs', []).directive('hichart', function () {

  return {
    restrict: 'E',
    template: '<div></div>',
    replace: true,
    link: function (scope, element, attrs) {
    {
        var chart1 = new Highcharts.Chart({
          chart: {
            renderTo: attrs.id,
            type: attrs.charttype,
            height: attrs.chartheight,
            animation: false
          },
          series: [{
               name:'click'
            }]
          

        })
		var redraw = function (newScopeData) {
		        if (newScopeData) {
	            	chart1.series[0].setData(newScopeData.data)
	            	chart1.series[0].name = newScopeData.name
	            	chart1.redraw();
	            	}
	        };
	        scope.$watch(attrs.data, redraw, true);
	      }
	    }
  };
})
