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
    // --- Simple tracking call ---
    fetch("/track")
        .catch(err => console.log("Tracker failed:", err))
        .then(() => console.log("Tracker success"));

});

function loadFragment(file, target, callback) {
    fetch(file)
        .then(response => response.text())
        .then(data => {
            document.querySelector(target).innerHTML = data;
            if (callback) callback(); // Run callback after fragment loads
        });
}

// ---------------- Header Init (Theme + Menu + Contact Modal) ---------------- //

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
            if (nav.classList.contains("active") &&
                !nav.contains(e.target) &&
                !menuBtn.contains(e.target)) {
                nav.classList.remove("active");
            }
        });
    }

    // ---- Contact Modal Logic ----
    const requestExecutiveButton = document.getElementById('requestExecutiveButton');
    const contactButton = document.getElementById('contactButton');
    const mailModal = document.getElementById('mailModal');
    const yesButton = document.getElementById('yesButton');
    const noButton = document.getElementById('noButton');
    const emailButton = document.getElementById('emailButton');

    let currentTrigger = null;

    if (!contactButton || !mailModal) return;

    // Show modal
    contactButton.addEventListener('click', () => {
        currentTrigger = 'contact';
        mailModal.style.display = 'flex';
    });

    // Also show modal when clicking request executive button
    requestExecutiveButton.addEventListener('click', () => {
        currentTrigger = 'requestExecutive';
        mailModal.style.display = 'flex';
    })


    // Also show modal when clicking email button
    emailButton?.addEventListener('click', (e) => {
        e.preventDefault(); // Prevent default anchor action
        currentTrigger = 'contact';
        mailModal.style.display = 'flex';
    });

    // Hide modal on No
    noButton?.addEventListener('click', () => {
        currentTrigger=null;
        mailModal.style.display = 'none';
    });

    // Redirect to mail app on Yes
    yesButton?.addEventListener('click', () => {
        let subject,body;

        if (currentTrigger === 'requestExecutive') {
            subject = encodeURIComponent("Executive Request - Fraxen Website");
            body = encodeURIComponent("Hello Fraxen team,\n\nI would like to request an executive consultation.\n\nMy phone number:\n");
        } else {
            subject = encodeURIComponent("Sent via Fraxen Website");
            body = encodeURIComponent("Hello Fraxen team,\n\nI would like to get in touch with you.\n\nBest regards,\n");
        }

        window.location.href = `mailto:admin@fraxen.eu?subject=${subject}&body=${body}`;
        mailModal.style.display = 'none';
    });

    // Close modal if clicking outside
    window.addEventListener('click', (e) => {
        if (e.target === mailModal) mailModal.style.display = 'none';
    });

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
