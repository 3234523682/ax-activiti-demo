$(function () {
    var Accordion = function (el, multiple) {
        this.el = el || {};
        this.multiple = multiple || false;

        // Variables privadas
        var links = this.el.find('.link');
        // Evento
        links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown)
    }

    Accordion.prototype.dropdown = function (e) {
        var $el = e.data.el;
        $this = $(this),
            $next = $this.next();

        $next.slideToggle();
        $this.parent().toggleClass('open');

        if (!e.data.multiple) {
            $el.find('.submenu').not($next).slideUp().parent().removeClass('open');
        }
        ;
    }

    var accordion = new Accordion($('#accordion'), false);

    $("#accordion .submenu li").click(function () {
        $("#accordion .submenu li").removeClass("submenuASelect");
        $(this).addClass("submenuASelect");
    });

    // 组提示
    $('.txtTitle').tooltip();
});

function operationFailureNotice(content) {
    new jBox('Notice', {
        attributes: {x: 'left', y: 'bottom'},
        stack: false,
        animation: {open: 'tada', close: 'zoomIn'},
        autoClose: Math.random() * 2000 + 2000,
        color: 'red',
        title: '温馨提示',
        content: content,
        delayOnHover: true,
        showCountdown: true,
        closeButton: true
    });
}

function operationSucceedNotice(content, closeFun) {
    new jBox('Notice', {
        attributes: {x: 'right', y: 'bottom'},
        stack: false,
        animation: {open: 'tada', close: 'zoomIn'},
        autoClose: Math.random() * 100 + 1000,
        color: 'green',
        title: '温馨提示',
        content: content,
        delayOnHover: true,
        showCountdown: true,
        closeButton: true,
        onClose: closeFun
    });
}