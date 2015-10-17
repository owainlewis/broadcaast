/*global Backbone */

$(function() {

    'use strict';

    window.App = {
        Collections: { },
        Models: { },
        Views: { },
    };

    App.Models.Channel = Backbone.Model.extend({

    });

    App.Collections.Channels = Backbone.Collection.extend({
        url: function () {
            return '/api/users/' + this.user + '/channels';
        },

        initialize: function ( models, opts ) {
            this.user = opts.user;
        }
    });

    App.Models.Comment = Backbone.Model.extend({
    });

    // var stream = new App.Collections.Comments({}, {user: 1, stream: 64})
    App.Collections.Items = Backbone.Collection.extend({
        url: function () {
            return '/api/users/' + this.user + '/channels/' + this.channel + '/items';
        },

        initialize: function ( models, opts ) {
            this.user   = opts.user;
            this.channel = opts.channel;
        }
    });

    // Views

    App.Views.Card = Backbone.View.extend({
        tagName: 'li',

        template: _.template($('#commentTemplate').html()),

        render: function ( ) {
             return this.template(this.model.toJSON());
        }
    });

    App.Views.Items = Backbone.View.extend({

            el: ".cards",

            initialize: function ( opts ) {
                this.channel = opts.channel;
                this.collection = new App.Collections.Items({}, { user: 1, channel: this.channel });
                this.collection.on('add', this.addOne, this);

                this.collection.fetch({
                    success: _.bind(function () {
                        this.render();
                    }, this)
                });
            },

            addOne: function (model) {
                this.$el.append(new App.Views.Card({model: model}).render());
            },

            render: function() {
                this.collection.each(this.addOne, this);
            }
    });
});
