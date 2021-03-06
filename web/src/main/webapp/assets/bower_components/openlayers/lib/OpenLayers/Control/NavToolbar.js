/* Copyright (c) 2006-2013 by OpenLayers Contributors (see authors.txt for
 * full list of contributors). Published under the 2-clause BSD license.
 * See license.txt in the OpenLayers distribution or repository for the
 * full text of the license. */


OpenLayers.Control.NavToolbar = OpenLayers.Class(OpenLayers.Control.Panel, {

        initialize: function(options) {
        OpenLayers.Control.Panel.prototype.initialize.apply(this, [options]);
        this.addControls([
          new OpenLayers.Control.Navigation(),
          new OpenLayers.Control.ZoomBox()
        ]);
    },

        draw: function() {
        var div = OpenLayers.Control.Panel.prototype.draw.apply(this, arguments);
        if (this.defaultControl === null) {
            this.defaultControl = this.controls[0];
        }
        return div;
    },

    CLASS_NAME: "OpenLayers.Control.NavToolbar"
});
