$(function() {

    $('#add-image').click(function(e){
        e.preventDefault();
        $('.image-input').toggle();
        return false;
    });

    $('#toggle-menu').click(function(e){
        e.preventDefault();
        $('.sub-header').toggle();
        return false;
    });
});
