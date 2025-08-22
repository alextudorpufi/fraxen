document.addEventListener("DOMContentLoaded", () => {
    // Load fragments first
    loadFragment("header.html", "#header", initHeader);
    loadFragment("footer.html", "#footer");

    // Fade-in animations
    const faders = document.querySelectorAll('.fade-in');
    const appearOptions = { threshold: 0.2 };
    const appearOnScroll = new IntersectionObserver((entries, observer) => {
        entries.forEach(entry => {
            if (!entry.isIntersecting) return;
            entry.target.classList.add('visible');
            observer.unobserve(entry.target);
        });
    }, appearOptions);

    faders.forEach(fader => appearOnScroll.observe(fader));
});

function loadFragment(file, target, callback) {
    fetch(file)
        .then(response => response.text())
        .then(data => {
            document.querySelector(target).innerHTML = data;
            if (callback) callback(); // Run callback after fragment loads
        });
}

// ---------------- Header Init (Theme + Menu) ---------------- //

function initHeader() {
    initThemeToggle();

    const menuBtn = document.querySelector(".menu-toggle");
    const nav = document.querySelector("header nav");

    if (menuBtn && nav) {
        // Toggle menu on button click
        menuBtn.addEventListener("click", () => {
            nav.classList.toggle("active");
        });

        // Close menu when clicking outside
        document.addEventListener("click", (e) => {
            // Only if menu is open
            if (nav.classList.contains("active")) {
                // Check if click is outside nav and menu button
                if (!nav.contains(e.target) && !menuBtn.contains(e.target)) {
                    nav.classList.remove("active");
                }
            }
        });
    }
}


// ---------------- Light/Dark Mode Toggle ---------------- //

function initThemeToggle() {
    const toggleBtn = document.getElementById("theme-toggle");
    const body = document.body;

    if (!toggleBtn) return;

    if (localStorage.getItem("theme") === "dark") {
        body.classList.add("dark-mode");
    }

    toggleBtn.addEventListener("click", () => {
        body.classList.toggle("dark-mode");
        const isDark = body.classList.contains("dark-mode");
        localStorage.setItem("theme", isDark ? "dark" : "light");
    });
}
