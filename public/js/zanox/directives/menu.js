angular.module('MainMenu', []).directive('mainmenu', function () {

  return {
    restrict: 'E',
    template: '<div></div>',
    replace: true,
    link: function (scope, element, attrs) {

        var chart = new Highcharts.Chart({
          chart: {
            renderTo: attrs.id,
            type: attrs.type,
            height: attrs.chartheight,
            animation: true
          },
          plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true
                    },
                    showInLegend: true
                }
            },
          series: [{
               name:'click'
            }]
          

        })
		var redraw = function (newScopeData) {
	        if (newScopeData) {
	            for (var i=0; i < newScopeData.length; i++) {
	            	chart1.series[i].setData(newScopeData[i].data)
	            	chart1.series[i].name = newScopeData[i].name
	            }
	        	chart.redraw();
	        }
        };
        //scope.$watch(attrs.data, redraw, true);
  	}
  };
})
