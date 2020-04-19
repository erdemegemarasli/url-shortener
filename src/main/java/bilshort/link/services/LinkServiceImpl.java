package bilshort.link.services;

import bilshort.link.models.Link;
import bilshort.link.models.LinkDTO;
import bilshort.link.repositories.LinkRepository;
import bilshort.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Link createShortLink(LinkDTO linkDTO) {
        Link link = new Link();
        link.setLongLink(linkDTO.getLongUrl());
        link.setTimeToLive(linkDTO.getExpTime());
        //Desc ekle
        link.setUserId(userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        //Sonra düzelt
        link.setShortLink(linkDTO.getShortUrl());
        return linkRepository.save(link);
    }

    @Override
    public List<Link> getAllLinks() {
        return linkRepository.findAll();
    }

    @Override
    public Link getLinkById(Integer id) {
        return linkRepository.findByLinkId(id);
    }

    @Override
    public Long deleteLinkById(Integer id) {
        return linkRepository.deleteByLinkId(id);
    }

    @Override
    public Link updateLink(Link link) {
        return linkRepository.save(link);
    }

    @Override
    public List<Link> getLinksByUserId(Integer userId) {
        return linkRepository.findByUserIdEx(userId);
    }
}
