;(function(window) {

  var svgSprite = '<svg>' +
    '' +
    '<symbol id="icon-jiantouzhidi" viewBox="0 0 1024 1024">' +
    '' +
    '<path d="M512 944.429665 941.285478 515.144188 683.71396 515.144188 683.71396 0 340.28604 0 340.28604 515.144188 82.714522 515.144188Z"  ></path>' +
    '' +
    '<path d="M82.714522 975.565888l858.570955 0 0 48.434124-858.570955 0 0-48.434124Z"  ></path>' +
    '' +
    '</symbol>' +
    '' +
    '<symbol id="icon-jiantouzhiding" viewBox="0 0 1024 1024">' +
    '' +
    '<path d="M512 79.570345 82.714527 508.855818 340.284888 508.855818 340.284888 1024 683.712805 1024 683.712805 508.855818 941.285473 508.855818Z"  ></path>' +
    '' +
    '<path d="M82.714527 0l858.570946 0 0 48.434123-858.570946 0 0-48.434123Z"  ></path>' +
    '' +
    '</symbol>' +
    '' +
    '<symbol id="icon-xiangxiajiantou" viewBox="0 0 1024 1024">' +
    '' +
    '<path d="M354.562295 63.224973l0 470.682989L185.649234 533.907962l326.34872 426.866041 326.352813-426.866041-168.917154 0L669.433612 63.224973 354.562295 63.224973z"  ></path>' +
    '' +
    '</symbol>' +
    '' +
    '<symbol id="icon-xiangshangjiantou" viewBox="0 0 1024 1024">' +
    '' +
    '<path d="M669.433612 960.774003 669.433612 490.092038l168.917154 0L511.997953 63.224973 185.649234 490.092038l168.913061 0 0 470.682989L669.433612 960.775027z"  ></path>' +
    '' +
    '</symbol>' +
    '' +
    '</svg>'
  var script = function() {
    var scripts = document.getElementsByTagName('script')
    return scripts[scripts.length - 1]
  }()
  var shouldInjectCss = script.getAttribute("data-injectcss")

  /**
   * document ready
   */
  var ready = function(fn) {
    if (document.addEventListener) {
      if (~["complete", "loaded", "interactive"].indexOf(document.readyState)) {
        setTimeout(fn, 0)
      } else {
        var loadFn = function() {
          document.removeEventListener("DOMContentLoaded", loadFn, false)
          fn()
        }
        document.addEventListener("DOMContentLoaded", loadFn, false)
      }
    } else if (document.attachEvent) {
      IEContentLoaded(window, fn)
    }

    function IEContentLoaded(w, fn) {
      var d = w.document,
        done = false,
        // only fire once
        init = function() {
          if (!done) {
            done = true
            fn()
          }
        }
        // polling for no errors
      var polling = function() {
        try {
          // throws errors until after ondocumentready
          d.documentElement.doScroll('left')
        } catch (e) {
          setTimeout(polling, 50)
          return
        }
        // no errors, fire

        init()
      };

      polling()
        // trying to always fire before onload
      d.onreadystatechange = function() {
        if (d.readyState == 'complete') {
          d.onreadystatechange = null
          init()
        }
      }
    }
  }

  /**
   * Insert el before target
   *
   * @param {Element} el
   * @param {Element} target
   */

  var before = function(el, target) {
    target.parentNode.insertBefore(el, target)
  }

  /**
   * Prepend el to target
   *
   * @param {Element} el
   * @param {Element} target
   */

  var prepend = function(el, target) {
    if (target.firstChild) {
      before(el, target.firstChild)
    } else {
      target.appendChild(el)
    }
  }

  function appendSvg() {
    var div, svg

    div = document.createElement('div')
    div.innerHTML = svgSprite
    svgSprite = null
    svg = div.getElementsByTagName('svg')[0]
    if (svg) {
      svg.setAttribute('aria-hidden', 'true')
      svg.style.position = 'absolute'
      svg.style.width = 0
      svg.style.height = 0
      svg.style.overflow = 'hidden'
      prepend(svg, document.body)
    }
  }

  if (shouldInjectCss && !window.__iconfont__svg__cssinject__) {
    window.__iconfont__svg__cssinject__ = true
    try {
      document.write("<style>.svgfont {display: inline-block;width: 1em;height: 1em;fill: currentColor;vertical-align: -0.1em;font-size:16px;}</style>");
    } catch (e) {
      console && console.log(e)
    }
  }

  ready(appendSvg)


})(window)