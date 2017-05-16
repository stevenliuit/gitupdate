;(function(window) {

  var svgSprite = '<svg>' +
    '' +
    '<symbol id="icon-zhiding" viewBox="0 0 1024 1024">' +
    '' +
    '<path d="M79.213 64.081h865.519v97.989h-865.519v-97.989zM512 177.255l432.731 446.737h-251.888l1.069 335.925h-362.7v-335.925h-252z"  ></path>' +
    '' +
    '</symbol>' +
    '' +
    '<symbol id="icon-iconzd2" viewBox="0 0 1024 1024">' +
    '' +
    '<path d="M120.418069 957.708178 120.418069 957.708178l0-99.245352 780.778536 0 0 99.245352L120.418069 957.708178 120.418069 957.708178zM120.418069 461.808878 510.643608 858.461803l390.552997-396.652925L705.864848 461.808878 705.864848 65.101717 315.638285 65.101717l0 396.707161L120.418069 461.808878 120.418069 461.808878z"  ></path>' +
    '' +
    '</symbol>' +
    '' +
    '<symbol id="icon-arrowdown" viewBox="0 0 1024 1024">' +
    '' +
    '<path d="M512 992l480-480-288 0 0-512-384 0 0 512-288 0z"  ></path>' +
    '' +
    '</symbol>' +
    '' +
    '<symbol id="icon-arrowup" viewBox="0 0 1024 1024">' +
    '' +
    '<path d="M512 32l-480 480 288 0 0 512 384 0 0-512 288 0z"  ></path>' +
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