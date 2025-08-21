document.addEventListener("DOMContentLoaded", () => {
    // Load fragments first
    loadFragment("header.html", "#header", initThemeToggle);
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

// ---------------- Light/Dark Mode Toggle ---------------- //

function initThemeToggle() {
    const toggleBtn = document.getElementById("theme-toggle");
    const body = document.body;

    if (!toggleBtn) return; // safety check

    if (localStorage.getItem("theme") === "dark") {
        body.classList.add("dark-mode");
    }

    toggleBtn.addEventListener("click", () => {
        body.classList.toggle("dark-mode");
        const isDark = body.classList.contains("dark-mode");
        localStorage.setItem("theme", isDark ? "dark" : "light");
    });
}
