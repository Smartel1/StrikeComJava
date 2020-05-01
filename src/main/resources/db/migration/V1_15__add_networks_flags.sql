alter table news
	add sent_to_ok boolean default false not null;
alter table news
	add sent_to_vk boolean default false not null;
alter table news
	add sent_to_telegram boolean default false not null;
alter table news
	add sent_to_twitter boolean default false not null;
alter table news
	add sent_to_instagram boolean default false not null;

alter table events
	add sent_to_ok boolean default false not null;
alter table events
	add sent_to_vk boolean default false not null;
alter table events
	add sent_to_telegram boolean default false not null;
alter table events
	add sent_to_twitter boolean default false not null;
alter table events
	add sent_to_instagram boolean default false not null;

update events set sent_to_ok = true, sent_to_vk = true,
    sent_to_telegram = true, sent_to_twitter = true, sent_to_instagram = true;

update news set sent_to_ok = true, sent_to_vk = true,
    sent_to_telegram = true, sent_to_twitter = true, sent_to_instagram = true;
